package com.platanitos.springplatanitos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.platanitos.springplatanitos.services.productoServices;
import org.springframework.web.bind.annotation.*;

import com.platanitos.springplatanitos.models.producto;
import com.platanitos.springplatanitos.models.payload.response;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class productoController {

    @Autowired
    private productoServices productoServices;

    //Metodo para traer todos los productos que esten activos
    @GetMapping("/todos")
    public ResponseEntity<response<List<producto>>> TodosLosProductos() {
        List<producto> productos = productoServices.TodosLosProductos();

        if(productos.isEmpty()) 
            return ResponseEntity.status(404).body(new response<>(false, "No se encontraron productos", null));
        

        return ResponseEntity.status(200).body(new response<>(true, "Productos encontrados", productos));
    }
    
    //Metodo para traer un producto por su id
    @GetMapping("/{id}")
    public ResponseEntity<response<producto>> ProductoPorId(@PathVariable Long id){
        producto producto = productoServices.ProductoPorId(id);

        if(producto == null) 
            return ResponseEntity.status(404).body(new response<>(false, "Producto no encontrado", null));
        

        return ResponseEntity.status(200).body(new response<>(true, "Producto encontrado", producto));
    }

    //Metodo para crear un nuevo producto
    @PostMapping
    public ResponseEntity<response<producto>> crearProducto(@RequestBody producto nuevoProducto){
        producto productoCreado = productoServices.CrearProducto(nuevoProducto);

        if(productoCreado == null) 
            return ResponseEntity.status(400).body(new response<>(false, "Error al crear el producto", null));
        

        return ResponseEntity.status(201).body(new response<>(true, "Producto creado exitosamente", productoCreado));
    }

    //Metodo para actualizar un producto
    @PatchMapping("actualizar/{id}")
    public ResponseEntity<response<producto>> actualizarProducto(@PathVariable Long id, @RequestBody producto productoActualizado){
        producto producto = productoServices.ActualizarProducto(id, productoActualizado);

        if(producto == null) 
            return ResponseEntity.status(404).body(new response<>(false, "Producto no encontrado", null));
        

        return ResponseEntity.status(200).body(new response<>(true, "Producto actualizado exitosamente", producto));
    }

    //Metodo para eliminar un producto (softDelete)
    @PatchMapping("/estado/{id}")
    public ResponseEntity<response<String>> eliminarProducto(@PathVariable Long id){
        producto productoExistente = productoServices.ProductoPorId(id);

        if (productoExistente == null) {
            return ResponseEntity.status(404).body(new response<>(false, "Producto no encontrado", null));
        }

        boolean eliminado = productoServices.EliminarProducto(id);

        boolean estado = productoServices.ProductoPorId(id).getEstado();
        String mensaje = estado ? "Producto activado exitosamente" : "Producto desactivado exitosamente";
        if(!eliminado)
            return ResponseEntity.status(404).body(new response<>(false, "Ocurrio un error al desactivar o activar el producto", null));
        
        return ResponseEntity.status(200).body(new response<>(true, mensaje, null));
    }

    //Metodo para contar productos por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<response<Integer>> contarProductosPorCategoria(@PathVariable String categoria){
        Integer count = productoServices.ContarProductosPorCategoria(categoria);
        return ResponseEntity.status(200).body(new response<>(true, "Cantidad de productos por categoria", count));
    }
}
