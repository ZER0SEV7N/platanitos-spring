package com.platanitos.springplatanitos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.carrito;

@Repository
public interface carritoRepository extends JpaRepository<carrito, Long> {
    
    carrito findByIdUsuarioAndIdProducto(Long idUsuario, Long idProducto);
    List<carrito> findByIdUsuario(Long idUsuario);
    
    
}
