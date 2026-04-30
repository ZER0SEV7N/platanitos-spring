package com.platanitos.springplatanitos.services.carrito;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platanitos.springplatanitos.models.*;
import com.platanitos.springplatanitos.models.dto.CarritoResponseDTO;
import com.platanitos.springplatanitos.repository.auth.UsuarioRepository;
import com.platanitos.springplatanitos.repository.carrito.CarritoDetalleRepository;
import com.platanitos.springplatanitos.repository.carrito.CarritoRepository;
import com.platanitos.springplatanitos.repository.producto.ProductoVarianteRepository;

import jakarta.transaction.Transactional;

//Servicio para gestionar el carrito de compras
@Service
public class CarritoServices {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoVarianteRepository varianteRepository;
    
    @Autowired
    private CarritoDetalleRepository detalleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Metodo para agregar un producto al carrito
    @Transactional
    public CarritoDetalle agregarAlCarrito(Long idUsuario, Long idVariante, Integer cantidad){
        ProductoVariante variante = varianteRepository.findById(idVariante).orElse(null);
        if(variante == null || cantidad <= 0 || variante.getStock() < cantidad)
            return null; //Variante no existe, cantidad no valida o stock insuficiente

        Carrito carrito = obtenerOCrearCarrito(idUsuario);

        CarritoDetalle itemExistente = detalleRepository.findByCarritoIdAndProductoVarianteId(carrito.getId(), idVariante);
        
        if(itemExistente != null){
            int nuevaCantidad = itemExistente.getCantidad() + cantidad;

            if (nuevaCantidad > (variante.getStock() + itemExistente.getCantidad())) 
                return null; 

            itemExistente.setCantidad(nuevaCantidad);
            itemExistente.setSubtotal(nuevaCantidad * variante.getPrecio());
            detalleRepository.save(itemExistente);
        } else {
            itemExistente = new CarritoDetalle();
            itemExistente.setCarrito(carrito);
            itemExistente.setProductoVariante(variante);
            itemExistente.setCantidad(cantidad);
            itemExistente.setSubtotal(cantidad * variante.getPrecio());
            detalleRepository.save(itemExistente);
            carrito.getDetalles().add(itemExistente);
        }
        variante.setStock(variante.getStock() - cantidad);
        varianteRepository.save(variante);

        recalcularTotal(carrito);
        return itemExistente;
    }

    //Metodo para obtener el carrito de un usuario
    public Carrito obtenerCarritoPorUsuario(Long idUsuario) {
        return obtenerOCrearCarrito(idUsuario);
    }   

    //Metodo para eliminar un producto del carrito
    @Transactional
    public boolean eliminarDelCarrito(Long idCarritoDetalle){
        CarritoDetalle item = detalleRepository.findById(idCarritoDetalle).orElse(null);

        if(item == null) return false; //Item no encontrado

        ProductoVariante variante = item.getProductoVariante();

        //Devolver el stock al eliminar el item del carrito
        if(variante != null){
            variante.setStock(variante.getStock() + item.getCantidad());
            varianteRepository.save(variante);
        }

        //Remover el item del carrito y eliminarlo de la base de datos
        Carrito carrito = item.getCarrito();
        carrito.getDetalles().remove(item);
        detalleRepository.delete(item);

        recalcularTotal(carrito);
        return true;
    }

    //Metodo para actualizar la cantidad de un producto en el carrito
    @Transactional
    public CarritoDetalle actualizarCantidad(Long idCarritoDetalle, Integer nuevaCantidad){
        //Buscar el item en el carrito
        CarritoDetalle item = detalleRepository.findById(idCarritoDetalle).orElse(null);
        
        if(item == null || nuevaCantidad <= 0)
            return null; //Item no encontrado o cantidad no valida

        ProductoVariante variante = item.getProductoVariante();
        int diferencia = nuevaCantidad - item.getCantidad();

        if (diferencia > 0 && diferencia > variante.getStock()) 
            return null; 

        item.setCantidad(nuevaCantidad);
        item.setSubtotal(nuevaCantidad * variante.getPrecio());
        detalleRepository.save(item);

        //Actualizar el stock de la variante
        variante.setStock(variante.getStock() - diferencia);
        varianteRepository.save(variante);

        recalcularTotal(item.getCarrito());
        return item;
    }

    //Metodo para vaciar el carrito de un usuario
    @Transactional
    public boolean vaciarCarrito(Long idUsuario){
        Carrito carrito = obtenerOCrearCarrito(idUsuario);
        if(carrito.getDetalles().isEmpty())
            return false; //Carrito ya esta vacio

        //Devolver el stock de cada item antes de eliminar
        for(CarritoDetalle item : carrito.getDetalles()){
            ProductoVariante variante = item.getProductoVariante();
            if(variante != null){
                variante.setStock(variante.getStock() + item.getCantidad());
                varianteRepository.save(variante);
            }
        }

        //Limpiar los detalles del carrito y actualizar el total
        carrito.getDetalles().clear();
        carrito.setTotal(0.0);
        carritoRepository.save(carrito);
        return true;
    }

    //Metodo para buscar el carrito del usuario, o crear uno nuevo
    private Carrito obtenerOCrearCarrito(Long idUsuario){
        List<Carrito> carrito = carritoRepository.findByUsuarioId(idUsuario);
        if(!carrito.isEmpty())
            return carrito.get(0); //Retorna el primer carrito encontrado para el usuario

        Carrito nuevoCarrito = new Carrito();

        //Asociar el carrito al usuario
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

        //Setear el usuario y total inicial del carrito
        nuevoCarrito.setUsuario(usuario);
        nuevoCarrito.setTotal(0.0);
        return carritoRepository.save(nuevoCarrito);
    } 

    //Metodo para recalcular el total del carrito
    private void recalcularTotal(Carrito carrito){
        double total = carrito.getDetalles().stream()
                            .mapToDouble(CarritoDetalle::getSubtotal)
                            .sum();
        carrito.setTotal(total);
        carritoRepository.save(carrito);
    }

    //Método para convertir la entidad Carrito a un DTO aplanado
    public CarritoResponseDTO convertirDTO(Carrito carrito) {
        CarritoResponseDTO dto = new CarritoResponseDTO();
        dto.setIdCarrito(carrito.getId());
        dto.setTotal(carrito.getTotal());

        if (carrito.getDetalles() != null) {
            List<CarritoResponseDTO.DetalleCarritoDTO> detallesDto = carrito.getDetalles().stream()
                .map(item -> {
                    CarritoResponseDTO.DetalleCarritoDTO itemDto = new CarritoResponseDTO.DetalleCarritoDTO();
                    itemDto.setIdCarritoDetalle(item.getId());
                    itemDto.setCantidad(item.getCantidad());
                    itemDto.setSubtotal(item.getSubtotal());

                    if (item.getProductoVariante() != null) {
                        itemDto.setIdVariante(item.getProductoVariante().getId());
                        
                        if (item.getProductoVariante().getProducto() != null) 
                            itemDto.setProducto(item.getProductoVariante().getProducto().getProducto());
                            
                        if (item.getProductoVariante().getColor() != null) 
                            itemDto.setColor(item.getProductoVariante().getColor().getColor());
                            
                        if (item.getProductoVariante().getTalla() != null) 
                            itemDto.setTalla(item.getProductoVariante().getTalla().getTalla());
                    }
                    
                    return itemDto;
                }).collect(Collectors.toList());
            
            dto.setDetalles(detallesDto);
        } else {
            dto.setDetalles(new java.util.ArrayList<>());
        }

        return dto;
    }
}
