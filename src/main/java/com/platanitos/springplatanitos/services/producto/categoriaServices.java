package com.platanitos.springplatanitos.services.producto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platanitos.springplatanitos.models.Categoria;
import com.platanitos.springplatanitos.repository.producto.CategoriaRepository;

@Service
public class CategoriaServices {

    @Autowired
    private CategoriaRepository categoriaRepository;

    //Metodo para contar productos por categoria
    public Integer ContarProductosPorCategoria(String categoria){
        return categoriaRepository.countByCategoria(categoria);
    }

    //Metodo para mostrar todas las categorias
    public List<Categoria> TodasLasCategorias(){
        return categoriaRepository.findAll();
    }
}
