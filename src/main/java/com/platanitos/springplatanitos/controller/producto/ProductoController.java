package com.platanitos.springplatanitos.controller.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.platanitos.springplatanitos.models.Producto;
import com.platanitos.springplatanitos.models.dto.ProductoResponseDTO;
import com.platanitos.springplatanitos.models.payload.Response;
import com.platanitos.springplatanitos.services.producto.ProductoServices;
import java.util.List;
import java.util.stream.Collectors;

//Controlador para manejar las solicitudes relacionadas con los productos
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoServices productoServices;

    //Metodo para traer todos los productos que esten activos
    //Api: /api/productos/todos
    @GetMapping("/todos")
    public ResponseEntity<Response<List<ProductoResponseDTO>>> todosLosProductos() {
        try{
            List<Producto> productos = productoServices.todosLosProductos();

            //Si no se encuentran productos, retornar un mensaje adecuado
            if(productos.isEmpty()) 
                return ResponseEntity.status(404).body(new Response<>(false, "No se encontraron productos", null));

            List<ProductoResponseDTO> productosDTO = productos.stream()
                .map(producto -> productoServices.convertirDTO(producto)) // Usamos el traductor
                .collect(Collectors.toList());

            return ResponseEntity.status(200).body(new Response<>(true, "Productos encontrados", productosDTO));
        } catch(Exception e){
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al obtener los productos", null));
        }
    }
    
    //Metodo para traer un producto por su id
    //Api: /api/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductoResponseDTO>> productoPorId(@PathVariable Long id){
        try{
            Producto producto = productoServices.productoPorId(id);

            //Si no se encuentra el producto, retornar un mensaje adecuado
            if(producto == null) 
                return ResponseEntity.status(404).body(new Response<>(false, "Producto no encontrado", null));
            
            ProductoResponseDTO productoDTO = productoServices.convertirDTO(producto); // Convertimos a DTO antes de retornar

            return ResponseEntity.status(200).body(new Response<>(true, "Producto encontrado", productoDTO));
        } catch(Exception e){
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al obtener el producto por id: " + id, null));
        }
    }

    //Metodo para crear un nuevo producto
    //Api: /api/productos - POST
    @PostMapping
    public ResponseEntity<Response<ProductoResponseDTO>> crearProducto(@RequestBody Producto nuevoProducto){
        try {
            Producto productoCreado = productoServices.crearProducto(nuevoProducto);

            //Si no se pudo crear el producto, retornar un mensaje adecuado
            if(productoCreado == null) 
                return ResponseEntity.status(400).body(new Response<>(false, "Error al crear el producto", null));
            
            ProductoResponseDTO productoDTO = productoServices.convertirDTO(productoCreado); // Convertimos a DTO antes de retornar

            return ResponseEntity.status(201).body(new Response<>(true, "Producto creado exitosamente", productoDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al crear el producto", null));
        }
    }

    //Metodo para filtrar productos por categoria
    //Api: /api/productos/filtrar/{categoria}
    @GetMapping("/filtrar/{categoria}")
    public ResponseEntity<Response<List<ProductoResponseDTO>>> filtrarProductosPorCategoria(@PathVariable String categoria){
        try{
            List<Producto> productos = productoServices.filtrarProductosPorCategoria(categoria);

            if(productos.isEmpty()) 
                return ResponseEntity.status(404).body(new Response<>(false, "No se encontraron productos para la categoria: " + categoria, null));
            
            List<ProductoResponseDTO> productosDTO = productos.stream()
                .map(producto -> productoServices.convertirDTO(producto)) // Convertimos a DTO antes de retornar
                .collect(Collectors.toList());

            return ResponseEntity.status(200).body(new Response<>(true, "Productos encontrados para la categoria: " + categoria, productosDTO));
        } catch(Exception e){
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al filtrar los productos por categoria: " + categoria, null));
        }
    }

    //Metodo para actualizar un producto
    //Api: /api/productos/actualizar/{id} - PATCH
    @PatchMapping("actualizar/{id}")
    public ResponseEntity<Response<ProductoResponseDTO>> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado){
        try {
            Producto producto = productoServices.actualizarProducto(id, productoActualizado);

            if(producto == null) 
                return ResponseEntity.status(404).body(new Response<>(false, "Producto no encontrado", null));
        
            ProductoResponseDTO productoDTO = productoServices.convertirDTO(producto); // Convertimos a DTO antes de retornar

            return ResponseEntity.status(200).body(new Response<>(true, "Producto actualizado exitosamente", productoDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al actualizar el producto", null));
        }
    }

    //Metodo para eliminar un producto (softDelete)
    //Api: /api/productos/estado/{id} - PATCH
    @PatchMapping("/estado/{id}")
    public ResponseEntity<Response<Producto>> eliminarProducto(@PathVariable Long id){
        try {
            Producto producto = productoServices.cambiarEstado(id);

            if (producto == null) 
                return ResponseEntity.status(404).body(new Response<>(false, "Producto no encontrado o error al cambiar estado", null));
    
            String mensaje = producto.getEstado() ? "Producto activado exitosamente" : "Producto desactivado exitosamente";        
            return ResponseEntity.status(200).body(new Response<>(true, mensaje, producto));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al cambiar el estado del producto", null));
        }
    }
}
