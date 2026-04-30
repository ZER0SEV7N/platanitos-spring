package com.platanitos.springplatanitos.controller.producto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.platanitos.springplatanitos.models.ProductoVariante;
import com.platanitos.springplatanitos.models.dto.VarianteResponseDTO;
import com.platanitos.springplatanitos.models.payload.Response;
import com.platanitos.springplatanitos.services.producto.ProductoVarianteServices;


//Controlador para manejar las solicitudes relacionadas con las variantes de productos
@RestController
@RequestMapping("/api/producto-variantes")
public class ProductoVarianteController {
    
    @Autowired
    private ProductoVarianteServices productoVariantesService;

    //Metodo para traer todas las variantes de un producto que esten activas
    //Api: /api/producto-variantes/producto/{idProducto}
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<Response<List<VarianteResponseDTO>>> obtenerVariantesPorProducto(@PathVariable Long idProducto){
        try{
            List<VarianteResponseDTO> variantes = productoVariantesService.obtenerVariantesPorProductoId(idProducto).stream()
                    .map(productoVariantesService::convertirDTO)
                    .collect(java.util.stream.Collectors.toList());
            //Si no se encuentran variantes, retornar un mensaje adecuado
            if(variantes.isEmpty()) 
                return ResponseEntity.status(404).body(new Response<>(false, "No se encontraron variantes para el producto con id: " + idProducto, null));

            return ResponseEntity.status(200).body(new Response<>(true, "Variantes encontradas para el producto con id: " + idProducto, variantes));
        }catch (Exception e){
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al obtener las variantes del producto con id: " + idProducto, null));
        }
    }

    //Metodo para traer todas las variantes que esten activas
    //Api: /api/producto-variantes/todos
    @GetMapping("/todos")
    public ResponseEntity<Response<List<VarianteResponseDTO>>> obtenerTodasLasVariantes(){
        try{
            List<VarianteResponseDTO> variantes = productoVariantesService.todasLasVariantes().stream()
                    .map(productoVariantesService::convertirDTO)
                    .collect(java.util.stream.Collectors.toList());

            //Si no se encuentran variantes, retornar un mensaje adecuado
            if(variantes.isEmpty()) 
                return ResponseEntity.status(404).body(new Response<>(false, "No se encontraron variantes activas", null));

            return ResponseEntity.status(200).body(new Response<>(true, "Variantes activas encontradas", variantes));
        }catch (Exception e){
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al obtener las variantes activas", null));
        }
    }
    
    //Metodo para traer una variante por su id
    //Api: /api/producto-variantes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Response<VarianteResponseDTO>> obtenerVariantePorId(@PathVariable Long id){
        try{
            ProductoVariante variante = productoVariantesService.obtenerVariantePorId(id);
            VarianteResponseDTO varianteDTO = productoVariantesService.convertirDTO(variante);

            //Si no se encuentra la variante, retornar un mensaje adecuado
            if(varianteDTO == null)
                return ResponseEntity.status(404).body(new Response<>(false, "Variante no encontrada con id: " + id, null));

            return ResponseEntity.status(200).body(new Response<>(true, "Variante encontrada con id: " + id, varianteDTO));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al obtener la variante con id: " + id, null));
        }
    }

    //Metodo para contar el numero de variantes que existen con una talla y color especificos
    //Api: /api/producto-variantes/contar?talla={talla}&color={color}&idProducto={idProducto}
    @GetMapping("/contar")
    public ResponseEntity<Response<Integer>> contarVariantesPorTallaYColor(@RequestParam String talla, @RequestParam String color, @RequestParam Long idProducto){
        try{
            Integer cantidad = productoVariantesService.contarVariantesPorTallaYColor(talla, color, idProducto);
            return ResponseEntity.status(200).body(new Response<>(true, "Cantidad de variantes con talla: " + talla + ", color: " + color + " para el producto con id: " + idProducto, cantidad));
        }catch (Exception e){
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al contar las variantes con talla: " + talla + ", color: " + color + " para el producto con id: " + idProducto, null));
        }
    }

    //Metodo para crear una nueva variante de producto
    //Api: /api/producto-variantes - POST
    @PostMapping
    public ResponseEntity<Response<VarianteResponseDTO>> crearVariante(@RequestBody ProductoVariante nuevaVariante){
        try{
            ProductoVariante variante = productoVariantesService.crearVariante(nuevaVariante);
            //Si no se pudo crear la variante, retornar un mensaje adecuado
            if(variante == null)
                return ResponseEntity.status(400).body(new Response<>(false, "Error al crear la variante", null));

            VarianteResponseDTO varianteDTO = productoVariantesService.convertirDTO(variante);

            return ResponseEntity.status(201).body(new Response<>(true, "Variante creada exitosamente", varianteDTO));
        }catch (Exception e){
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al crear la variante", null));
        }
    }
    
    //Metodo para actualizar una variante de producto
    //Api: /api/producto-variantes/actualizar/{id} - PATCH
    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<Response<VarianteResponseDTO>> actualizarVariante(@PathVariable Long id, @RequestBody ProductoVariante varianteActualizada){
        try {
            ProductoVariante variante = productoVariantesService.actualizarVariante(id, varianteActualizada);

            //Si no se encuentra la variante a actualizar, retornar un mensaje adecuado
            if(variante == null) 
                return ResponseEntity.status(404).body(new Response<>(false, "Variante no encontrada con id: " + id, null));

            VarianteResponseDTO varianteDTO = productoVariantesService.convertirDTO(variante);
            return ResponseEntity.status(200).body(new Response<>(true, "Variante actualizada con id: " + id, varianteDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al actualizar la variante con id: " + id, null));
        }
    }

    //Metodo para eliminar una variante de producto (softDelete)
    //Api: /api/producto-variantes/estado/{id} - PATCH
    @PatchMapping("/estado/{id}")
    public ResponseEntity<Response<VarianteResponseDTO>> eliminarVariante(@PathVariable Long id){
        try {
            ProductoVariante variante = productoVariantesService.cambiarEstado(id);

            //Si no se encuentra la variante a eliminar, retornar un mensaje adecuado
            if(variante == null) 
                return ResponseEntity.status(404).body(new Response<>(false, "Variante no encontrada con id: " + id, null));

            String mensaje = variante.getEstado() ? "Variante activada exitosamente" : "Variante desactivada exitosamente";        
            VarianteResponseDTO varianteDTO = productoVariantesService.convertirDTO(variante);
            return ResponseEntity.status(200).body(new Response<>(true, mensaje, varianteDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response<>(false, "Ocurrio un error al cambiar el estado de la variante con id: " + id, null));
        }
    }
}
