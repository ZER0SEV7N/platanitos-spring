package com.platanitos.springplatanitos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platanitos.springplatanitos.repository.carritoRepository;
import com.platanitos.springplatanitos.models.*;

@Service
public class carritoServices {

    @Autowired
    private carritoRepository carritoRepository;
    @Autowired
    private productoServices productoServices;

    public carrito AgregarAlCarrito(carrito nuevoCarrito){
        
        producto productoDB = productoServices.ProductoPorId(nuevoCarrito.getIdProducto());
        if(productoDB == null || productoDB.getStock() <= 0)
            return null; 
        
        carrito itemExistente = carritoRepository.findByIdUsuarioAndIdProducto(
            nuevoCarrito.getIdUsuario(), 
            nuevoCarrito.getIdProducto()
        );
        
        if(itemExistente  != null){
            int nuevaCantidad = itemExistente.getCantidad() + nuevoCarrito.getCantidad();

            if(nuevaCantidad > productoDB.getStock())
                return null;

            itemExistente.setCantidad(nuevaCantidad);
            carritoRepository.save(itemExistente);
        } else {
            if(nuevoCarrito.getCantidad() > productoDB.getStock())
                nuevoCarrito.setCantidad(1);

            carritoRepository.save(nuevoCarrito);
        }

        int stock = productoDB.getStock() - nuevoCarrito.getCantidad();
        productoDB.setStock(stock);

        productoServices.ActualizarProducto(productoDB.getId(), productoDB);
        return itemExistente != null ? itemExistente : nuevoCarrito;
    }

    public List<carrito> ObtenerCarritoPorUsuario(Long idUsuario){
        return carritoRepository.findByIdUsuario(idUsuario);
    }

    public boolean EliminarDelCarrito(Long idCarritoItem){
        carrito item = carritoRepository.findById(idCarritoItem).orElse(null);
        
        if(item == null)
            return false;

        producto productoDB = productoServices.ProductoPorId(item.getIdProducto());
        
        if (productoDB != null) {
            int stockDevuelto = productoDB.getStock() + item.getCantidad();
            productoDB.setStock(stockDevuelto);
            productoServices.ActualizarProducto(productoDB.getId(), productoDB);
        }

        carritoRepository.delete(item);
        return true;
    }

    public boolean VaciarCarrito(Long idUsuario){
        List<carrito> items = carritoRepository.findByIdUsuario(idUsuario);
        
        if(items.isEmpty())
            return false;

        for (carrito item : items) {
            producto productoDB = productoServices.ProductoPorId(item.getIdProducto());
            
            if (productoDB != null) {
                int stockDevuelto = productoDB.getStock() + item.getCantidad();
                productoDB.setStock(stockDevuelto);
                productoServices.ActualizarProducto(productoDB.getId(), productoDB);
            }
        }

        carritoRepository.deleteAll(items);
        return true;
    }

    public carrito ActualizarCantidad(Long idCarritoItem, Integer nuevaCantidad){
        
        if(nuevaCantidad <= 0) {
            EliminarDelCarrito(idCarritoItem);
            return null; 
        }

        carrito itemExistente = carritoRepository.findById(idCarritoItem).orElse(null);
        if (itemExistente == null) {
            return null;
        }

        producto productoDB = productoServices.ProductoPorId(itemExistente.getIdProducto());
        if (productoDB == null) {
            return null;
        }

        int diferencia = nuevaCantidad - itemExistente.getCantidad();

        if (diferencia > 0) {
            if (productoDB.getStock() < diferencia) {
                return null; 
            }
            productoDB.setStock(productoDB.getStock() - diferencia);
        } 

        else if (diferencia < 0) {
            productoDB.setStock(productoDB.getStock() - diferencia); 
        }

        productoServices.ActualizarProducto(productoDB.getId(), productoDB);
        
        itemExistente.setCantidad(nuevaCantidad);
        return carritoRepository.save(itemExistente);
    }
}
