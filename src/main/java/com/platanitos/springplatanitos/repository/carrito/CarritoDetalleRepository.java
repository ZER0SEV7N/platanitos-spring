package com.platanitos.springplatanitos.repository.carrito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.CarritoDetalle;

@Repository
public interface CarritoDetalleRepository extends JpaRepository<CarritoDetalle, Long> {

    //Consulta personalizada para filtrar por carrito y producto variante
    CarritoDetalle findByCarritoIdAndProductoVarianteId(Long idCarrito, Long idVariante);
}
