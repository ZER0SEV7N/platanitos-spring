package com.platanitos.springplatanitos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.platanitos.springplatanitos.models.categoria;

import com.platanitos.springplatanitos.models.payload.response;
import com.platanitos.springplatanitos.services.categoriaServices;

@RestController
@RequestMapping("/api/categorias")
public class categoriaController {

    @Autowired
    private categoriaServices categoriaServices;

    //Metodo para contar productos por categoria
    @GetMapping("/total/{categoria}")
    public ResponseEntity<response<Integer>> ContarProductosPorCategoria(@PathVariable String categoria){
        Integer count = categoriaServices.ContarProductosPorCategoria(categoria);

        if(count == null) 
            return ResponseEntity.status(404).body(new response<>(false, "Categoría no encontrada", null));
        
        return ResponseEntity.status(200).body(new response<>(true, "Cantidad de productos en la categoría: " + categoria, count));
    }

    //Metodo para mostrar todas las categorias
    @GetMapping
    public ResponseEntity<response<List<categoria>>> TodasLasCategorias(){
        return ResponseEntity.status(200).body(new response<>(true, "Categorías encontradas", categoriaServices.TodasLasCategorias()));
    }

}
