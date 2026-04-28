package com.platanitos.springplatanitos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platanitos.springplatanitos.models.color;
import com.platanitos.springplatanitos.repository.colorRepository;

@Service
public class colorServices {

    @Autowired
    private colorRepository colorRepository;

    public Integer ContarProductosPorColor(String color){
        return colorRepository.countByColor(color);
    }

    public List<color> TodosLosColores(){
        return colorRepository.findAll();
    }
}
