package com.platanitos.springplatanitos.repository.carrito;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {    
    
    //Consulta personalizada para filtrar por usuario
    List<Carrito> findByUsuarioId(Long idUsuario);
}
