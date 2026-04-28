package com.platanitos.springplatanitos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platanitos.springplatanitos.repository.carritoRepository;
import com.platanitos.springplatanitos.models.carrito;

@Service
public class carritoServices {

    @Autowired
    private carritoRepository carritoRepository;
    @Autowired
    private productoServices productoServices;

    public carrito AgregarAlCarrito(carrito nuevoCarrito){
        
    }

}
