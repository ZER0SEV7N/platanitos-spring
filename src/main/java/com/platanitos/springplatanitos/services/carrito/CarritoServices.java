package com.platanitos.springplatanitos.services.carrito;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platanitos.springplatanitos.models.*;
import com.platanitos.springplatanitos.repository.auth.UsuarioRepository;
import com.platanitos.springplatanitos.repository.carrito.CarritoDetalleRepository;
import com.platanitos.springplatanitos.repository.carrito.CarritoRepository;
import com.platanitos.springplatanitos.repository.producto.ProductoVarianteRepository;
import com.platanitos.springplatanitos.services.producto.ProductoServices;

import jakarta.transaction.Transactional;

//Servicio para gestionar el carrito de compras
@Service
public class CarritoServices {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoVarianteRepository varianteRepository;
    
    @Autowired
    private CarritoDetalleRepository carritoDetalleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Metodo para buscar el carrito del usuario, o crear uno nuevo
    private Carrito obtenerOCrearCarrito(Long idUsuario){
        List<Carrito> carrito = carritoRepository.findByUsuarioId(idUsuario);
        if(!carrito.isEmpty())
            return carrito.get(0); //Retorna el primer carrito encontrado para el usuario

        Carrito nuevoCarrito = new Carrito();
        nuevoCarrito.setUsuario(usuarioRepository.findById(idUsuario).orElse(null));
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

    //Metodo para agregar un producto al carrito
    @Transactional
    public CarritoDetalle agregarAlCarrito(Long idUsuario, Long idVariante, Integer cantidad){
        ProductoVariante variante = varianteRepository.findById(idVariante).orElse(null);
        if(variante == null || cantidad <= 0 || variante.getStock() < cantidad)
            return null; //Variante no existe, cantidad no valida o stock insuficiente

        Carrito carrito = obtenerOCrearCarrito(idUsuario);

        CarritoDetalle itemExistente = carritoDetalleRepository.findByCarritoIdAndProductoVarianteId(carrito.getId(), idVariante);
        
        if(itemExistente != null){
            int nuevaCantidad = itemExistente.getCantidad() + cantidad;

            if (nuevaCantidad > (variante.getStock() + itemExistente.getCantidad())) 
                return null; 

            itemExistente.setCantidad(nuevaCantidad);
            itemExistente.setSubtotal(nuevaCantidad * variante.getPrecio());
            carritoDetalleRepository.save(itemExistente);
        } else {
            itemExistente = new CarritoDetalle();
            itemExistente.setCarrito(carrito);
            itemExistente.setProductoVariante(variante);
            itemExistente.setCantidad(cantidad);
            itemExistente.setSubtotal(cantidad * variante.getPrecio());
            carritoDetalleRepository.save(itemExistente);
            carrito.getDetalles().add(itemExistente);
        }
        variante.setStock(variante.getStock() - cantidad);
        varianteRepository.save(variante);

        recalcularTotal(carrito);
        return itemExistente;
    }

    //Metodo para obtener el carrito de un usuario
    public Carrito getCarritoByUsuarioId(Long idUsuario) {
        return obtenerOCrearCarrito(idUsuario);
    }   
}
