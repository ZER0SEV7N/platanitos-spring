package com.platanitos.springplatanitos.services.producto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platanitos.springplatanitos.models.Color;
import com.platanitos.springplatanitos.repository.producto.ColorRepository;

@Service
public class colorServices {

    @Autowired
    private ColorRepository colorRepository;

    public Integer ContarProductosPorColor(String color){
        return colorRepository.countByColor(color);
    }

    public List<Color> TodosLosColores(){
        return colorRepository.findAll();
    }
}
