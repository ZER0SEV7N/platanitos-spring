package com.platanitos.springplatanitos.services.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platanitos.springplatanitos.repository.producto.ProductoRepository;
import com.platanitos.springplatanitos.models.*;
import com.platanitos.springplatanitos.models.dto.ProductoResponseDTO;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductoServices {

    //Inyectar la dependencia
    @Autowired
    private ProductoRepository productoRepository;

    //Metodo para traer todos los productos que esten activos
    public List<Producto> todosLosProductos() {
        return productoRepository.findAllByEstadoTrue();
    }

    //Metodo para traer un producto por su id
    public Producto productoPorId(Long id){
        return productoRepository.findById(id)
                                 .orElse(null);
    }

    //Metodo para crear un nuevo producto
    public Producto crearProducto(Producto nuevoProducto){
        return productoRepository.save(nuevoProducto);
    }

    //Metodo para actualizar un producto
    public Producto actualizarProducto(Long id, Producto productoActualizado){
        Optional<Producto> productoExistente = productoRepository.findById(id);

        if(productoExistente.isPresent())
            return null;
    
        Producto producto = productoExistente.get();
        producto.setProducto(productoActualizado.getProducto());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setCategoria(productoActualizado.getCategoria());
        return productoRepository.save(producto);
   }

    //Metodo para cambiar el estado de un producto (softDelete)
    public Producto cambiarEstado(Long id){
        Optional<Producto> productoExistente = productoRepository.findById(id);

        if(productoExistente.isPresent()){
            Producto producto = productoExistente.get();
            producto.setEstado(!producto.getEstado());
            return productoRepository.save(producto);
        } else 
            return null;
    }

    //Metodo para filtrar productos por categoria
    public List<Producto> filtrarProductosPorCategoria(String categoria){
        return productoRepository.findByCategoria_Categoria(categoria);
    }

    //Metodo para convertir la entidad Producto a un DTO
    public ProductoResponseDTO convertirDTO(Producto producto){

        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setProducto(producto.getProducto());
        dto.setDescripcion(producto.getDescripcion());
        
        //Obtener la categoria mientras no este vacio
        if (producto.getCategoria() != null) 
            dto.setCategoria(producto.getCategoria().getCategoria());

        if (producto.getVariantes() != null) {
            List<ProductoResponseDTO.VarianteDTO> variantesDto = producto.getVariantes().stream()
                .filter(v -> v.getEstado()) 
                .map(v -> {
                    ProductoResponseDTO.VarianteDTO vDto = new ProductoResponseDTO.VarianteDTO();
                    vDto.setIdVariante(v.getId());
                    vDto.setPrecio(v.getPrecio());
                    vDto.setStock(v.getStock());
                    
                    if (v.getColor() != null) vDto.setColor(v.getColor().getColor());
                    if (v.getTalla() != null) vDto.setTalla(v.getTalla().getTalla());
                    
                    return vDto;
                }).collect(Collectors.toList());
            
            dto.setVariantes(variantesDto);
        }
        
        return dto;
    }

}
