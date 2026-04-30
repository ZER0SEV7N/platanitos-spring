package com.platanitos.springplatanitos.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platanitos.springplatanitos.models.Carrito;
import com.platanitos.springplatanitos.models.payload.Response;
import com.platanitos.springplatanitos.services.carrito.CarritoServices;

@RestController
@RequestMapping("/api/carrito")
public class carritoController {

    @Autowired
    private CarritoServices carritoServices;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Response<List<Carrito>>> obtenerCarrito(@PathVariable Long idUsuario) {
        List<Carrito> miCarrito = carritoServices.ObtenerCarritoPorUsuario(idUsuario);
        
        if (miCarrito.isEmpty()) 
            return ResponseEntity.status(404).body(new Response<>(false, "El carrito está vacío", null));
        
        return ResponseEntity.status(200).body(new Response<>(true, "Carrito obtenido exitosamente", miCarrito));
    }

    @PostMapping("/agregar")
    public ResponseEntity<Response<Carrito>> agregarAlCarrito(@RequestBody Carrito nuevoCarrito) {
        Carrito itemAgregado = carritoServices.AgregarAlCarrito(nuevoCarrito);
        
        if (itemAgregado == null) 
            return ResponseEntity.status(400).body(new Response<>(false, "Error al agregar al carrito. Verifica el stock.", null));
        
        return ResponseEntity.status(201).body(new Response<>(true, "Producto agregado al carrito", itemAgregado));
    }

    @PatchMapping("/actualizar/{idCarritoItem}")
    public ResponseEntity<Response<Carrito>> actualizarCantidad(@PathVariable Long idCarritoItem, @RequestParam Integer cantidad) {
        Carrito itemActualizado = carritoServices.ActualizarCantidad(idCarritoItem, cantidad);
        
        if (itemActualizado == null) 
            return ResponseEntity.status(400).body(new Response<>(false, "No se pudo actualizar la cantidad. Verifica el stock o el ítem.", null));
        
        return ResponseEntity.status(200).body(new Response<>(true, "Cantidad actualizada", itemActualizado));
    }

    @DeleteMapping("/eliminar/{idCarritoItem}")
    public ResponseEntity<Response<String>> eliminarDelCarrito(@PathVariable Long idCarritoItem) {
        boolean eliminado = carritoServices.EliminarDelCarrito(idCarritoItem);
        
        if (!eliminado) 
            return ResponseEntity.status(404).body(new Response<>(false, "No se encontró el ítem en el carrito", null));
        
        return ResponseEntity.status(200).body(new Response<>(true, "Producto eliminado del carrito", null));
    }

    @DeleteMapping("/vaciar/{idUsuario}")
    public ResponseEntity<Response<String>> vaciarCarrito(@PathVariable Long idUsuario) {
        boolean vaciado = carritoServices.VaciarCarrito(idUsuario);
        
        if (!vaciado) 
            return ResponseEntity.status(404).body(new Response<>(false, "El carrito ya está vacío o no existe", null));
        
        return ResponseEntity.status(200).body(new Response<>(true, "Carrito vaciado exitosamente", null));
    }
}
