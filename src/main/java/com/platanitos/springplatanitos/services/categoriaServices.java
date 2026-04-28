package com.platanitos.springplatanitos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platanitos.springplatanitos.models.categoria;

import com.platanitos.springplatanitos.repository.categoriaRepository;

@Service
public class categoriaServices {

    @Autowired
    private categoriaRepository categoriaRepository;

    //Metodo para contar productos por categoria
    public Integer ContarProductosPorCategoria(String categoria){
        return categoriaRepository.countByCategoria(categoria);
    }

    //Metodo para mostrar todas las categorias
    public List<categoria> TodasLasCategorias(){
        return categoriaRepository.findAll();
    }
}
