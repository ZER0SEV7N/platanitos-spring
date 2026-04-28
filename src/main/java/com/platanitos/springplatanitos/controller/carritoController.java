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

import com.platanitos.springplatanitos.models.carrito;
import com.platanitos.springplatanitos.models.payload.response;
import com.platanitos.springplatanitos.services.carritoServices;

@RestController
@RequestMapping("/api/carrito")
public class carritoController {

    @Autowired
    private carritoServices carritoServices;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<response<List<carrito>>> obtenerCarrito(@PathVariable Long idUsuario) {
        List<carrito> miCarrito = carritoServices.ObtenerCarritoPorUsuario(idUsuario);
        
        if (miCarrito.isEmpty()) 
            return ResponseEntity.status(404).body(new response<>(false, "El carrito está vacío", null));
        
        return ResponseEntity.status(200).body(new response<>(true, "Carrito obtenido exitosamente", miCarrito));
    }

    @PostMapping("/agregar")
    public ResponseEntity<response<carrito>> agregarAlCarrito(@RequestBody carrito nuevoCarrito) {
        carrito itemAgregado = carritoServices.AgregarAlCarrito(nuevoCarrito);
        
        if (itemAgregado == null) 
            return ResponseEntity.status(400).body(new response<>(false, "Error al agregar al carrito. Verifica el stock.", null));
        
        return ResponseEntity.status(201).body(new response<>(true, "Producto agregado al carrito", itemAgregado));
    }

    @PatchMapping("/actualizar/{idCarritoItem}")
    public ResponseEntity<response<carrito>> actualizarCantidad(@PathVariable Long idCarritoItem, @RequestParam Integer cantidad) {
        carrito itemActualizado = carritoServices.ActualizarCantidad(idCarritoItem, cantidad);
        
        if (itemActualizado == null) 
            return ResponseEntity.status(400).body(new response<>(false, "No se pudo actualizar la cantidad. Verifica el stock o el ítem.", null));
        
        return ResponseEntity.status(200).body(new response<>(true, "Cantidad actualizada", itemActualizado));
    }

    @DeleteMapping("/eliminar/{idCarritoItem}")
    public ResponseEntity<response<String>> eliminarDelCarrito(@PathVariable Long idCarritoItem) {
        boolean eliminado = carritoServices.EliminarDelCarrito(idCarritoItem);
        
        if (!eliminado) 
            return ResponseEntity.status(404).body(new response<>(false, "No se encontró el ítem en el carrito", null));
        
        return ResponseEntity.status(200).body(new response<>(true, "Producto eliminado del carrito", null));
    }

    @DeleteMapping("/vaciar/{idUsuario}")
    public ResponseEntity<response<String>> vaciarCarrito(@PathVariable Long idUsuario) {
        boolean vaciado = carritoServices.VaciarCarrito(idUsuario);
        
        if (!vaciado) 
            return ResponseEntity.status(404).body(new response<>(false, "El carrito ya está vacío o no existe", null));
        
        return ResponseEntity.status(200).body(new response<>(true, "Carrito vaciado exitosamente", null));
    }
}
