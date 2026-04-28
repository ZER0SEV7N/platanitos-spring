package com.platanitos.springplatanitos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.carrito;

@Repository
public interface carritoRepository extends JpaRepository<carrito, Long> {
    
}
