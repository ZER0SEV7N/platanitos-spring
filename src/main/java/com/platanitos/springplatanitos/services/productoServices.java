package com.platanitos.springplatanitos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platanitos.springplatanitos.repository.*;
import com.platanitos.springplatanitos.models.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class productoServices {

    //Inyectar la dependencia
    @Autowired
    private productoRepository productoRepository;

    //Metodo para traer todos los productos que esten activos
    public List<producto> TodosLosProductos() {
        return productoRepository.findAllByEstadoTrue();
    }

    //Metodo para traer un producto por su id
    public producto ProductoPorId(Long id){
        return productoRepository.findById(id)
                                 .orElse(null);
    }

    //Metodo para crear un nuevo producto
    public producto CrearProducto(producto nuevoProducto){
        return productoRepository.save(nuevoProducto);
    }

    //Metodo para actualizar un producto
    public producto ActualizarProducto(Long id, producto productoActualizado){
        Optional<producto> productoExistente = productoRepository.findById(id);

        if (productoExistente.isPresent()) {
            producto producto = productoExistente.get();
            producto.setProducto(productoActualizado.getProducto());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());
            producto.setColor(productoActualizado.getColor());
            producto.setTalla(productoActualizado.getTalla());
            producto.setCategoria(productoActualizado.getCategoria());
            return productoRepository.save(producto);
        } else {
            return null;
        }
    }

    //Metodo para eliminar un producto (softDelete)
    public boolean EliminarProducto(Long id){
        Optional<producto> productoExistente = productoRepository.findById(id);

        if(productoExistente.isPresent()){
            producto producto = productoExistente.get();
            if(producto.getEstado() == false) 
                producto.setEstado(true);                
            else 
                producto.setEstado(false);
            
            productoRepository.save(producto);
            return true;
        } else {
            return false;
        }
    }

    //Metodo para filtrar productos por categoria
    public List<producto> FiltrarProductosPorCategoria(String categoria){
        return productoRepository.findByCategoria_Categoria(categoria);
    }

}
