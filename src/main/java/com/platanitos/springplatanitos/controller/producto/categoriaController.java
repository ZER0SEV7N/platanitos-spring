package com.platanitos.springplatanitos.controller.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.platanitos.springplatanitos.models.Categoria;

import com.platanitos.springplatanitos.models.payload.Response;
import com.platanitos.springplatanitos.services.producto.CategoriaServices;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaServices categoriaServices;

    //Metodo para contar productos por categoria
    @GetMapping("/total/{categoria}")
    public ResponseEntity<Response<Integer>> ContarProductosPorCategoria(@PathVariable String categoria){
        Integer count = categoriaServices.ContarProductosPorCategoria(categoria);

        if(count == null) 
            return ResponseEntity.status(404).body(new Response<>(false, "Categoría no encontrada", null));
        
        return ResponseEntity.status(200).body(new Response<>(true, "Cantidad de productos en la categoría: " + categoria, count));
    }

    //Metodo para mostrar todas las categorias
    @GetMapping
    public ResponseEntity<Response<List<Categoria>>> TodasLasCategorias(){
        return ResponseEntity.status(200).body(new Response<>(true, "Categorías encontradas", categoriaServices.TodasLasCategorias()));
    }

}
