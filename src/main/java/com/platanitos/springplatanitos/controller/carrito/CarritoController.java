package com.platanitos.springplatanitos.controller.carrito;

import java.util.Map;

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
import com.platanitos.springplatanitos.models.CarritoDetalle;
import com.platanitos.springplatanitos.models.dto.CarritoDTO;
import com.platanitos.springplatanitos.models.dto.CarritoResponseDTO;
import com.platanitos.springplatanitos.models.payload.Response;
import com.platanitos.springplatanitos.services.carrito.CarritoServices;

//Controlador para gestionar las operaciones del carrito de compras
@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoServices carritoServices;

    //Obtener el carrito activo de un usuario
    //Get: /api/carrito/{idUsuario}
    @GetMapping("/{idUsuario}")
    public ResponseEntity<Response<CarritoResponseDTO>> obtenerCarrito(@PathVariable Long idUsuario){
        Carrito carrito = carritoServices.obtenerCarritoPorUsuario(idUsuario);

        if(carrito == null || carrito.getDetalles().isEmpty())
            return ResponseEntity.status(404).body(new Response<>(false, "Carrito vacío", null));

        CarritoResponseDTO dto = carritoServices.convertirDTO(carrito);

        return ResponseEntity.status(200).body(new Response<>(true, "Carrito obtenido", dto));
    }
     
    //Agregar un producto al carrito
    //Post: /api/carrito/agregar
    @PostMapping("/agregar")
    public ResponseEntity<Response<CarritoResponseDTO>> agregarAlCarrito(@RequestBody CarritoDTO carritoDto){
        try{
            Long idUsuario = carritoDto.getIdUsuario();
            boolean stockError = false;
            
            for(CarritoDTO.ItemCarritoDTO item : carritoDto.getItems()){
                CarritoDetalle detalle = carritoServices.agregarAlCarrito(idUsuario, item.getIdVariante(), item.getCantidad());
                if(detalle == null)
                    stockError = true;
            }

            Carrito carritoActualizado = carritoServices.obtenerCarritoPorUsuario(idUsuario);
            CarritoResponseDTO dto = carritoServices.convertirDTO(carritoActualizado);

            if(stockError)
                return ResponseEntity.status(207).body(new Response<>(true, "Producto agregado pero con limitaciones de stock", dto ));
            
            return ResponseEntity.status(200).body(new Response<>(true, "Producto agregado al carrito", dto));
        } catch(Exception e){
            return ResponseEntity.status(500).body(new Response<>(false, "Error al agregar al carrito: " + e.getMessage(), null));
        }
    }

    //Actualizar la cantidad de un producto en el carrito
    //Patch: /api/carrito/actualizar/{idCarritoDetalle}
    @PatchMapping("/actualizar/{idCarritoDetalle}")
    public ResponseEntity<Response<CarritoResponseDTO>> actualizarCarrito(@PathVariable Long idCarritoDetalle, 
        @RequestBody Map<String, Integer> requestBody){
        Integer nuevaCantidad = requestBody.get("cantidad");

        if (nuevaCantidad == null || nuevaCantidad <= 0) {
            return ResponseEntity.status(400).body(new Response<>(false, "La cantidad debe ser mayor a cero", null));
        }

        CarritoDetalle carritoActualizado = carritoServices.actualizarCantidad(idCarritoDetalle, nuevaCantidad);

        //Convertir en dto
        CarritoResponseDTO dto = carritoServices.convertirDTO(carritoActualizado.getCarrito());
        return ResponseEntity.status(200).body(new Response<>(true, "Cantidad actualizada", dto));
    }

    //Eliminar un producto del carrito
    //Delete: /api/carrito/eliminar/{idCarritoDetalle}
    @DeleteMapping("/eliminar/{idCarritoDetalle}")
    public ResponseEntity<Response<CarritoResponseDTO>> eliminarDelCarrito(@PathVariable Long idCarritoDetalle){
        boolean eliminado = carritoServices.eliminarDelCarrito(idCarritoDetalle);

        if(!eliminado)
            return ResponseEntity.status(404).body(new Response<>(false, "Producto no encontrado en el carrito", null));

        return ResponseEntity.status(200).body(new Response<>(true, "Producto eliminado del carrito", null));
    }


    //Vaciar el carrito de un usuario
    //Delete: /api/carrito/vaciar/{idUsuario}
    @DeleteMapping("/vaciar/{idUsuario}")
    public ResponseEntity<Response<CarritoResponseDTO>> vaciarCarrito(@PathVariable Long idUsuario){
        boolean vaciado = carritoServices.vaciarCarrito(idUsuario);

        if(!vaciado)
            return ResponseEntity.status(404).body(new Response<>(false, "Carrito ya está vacío o no encontrado", null));

        return ResponseEntity.status(200).body(new Response<>(true, "Carrito vaciado", null));
    }
}
