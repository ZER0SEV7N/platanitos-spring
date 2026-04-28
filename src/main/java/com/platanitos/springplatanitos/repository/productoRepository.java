package com.platanitos.springplatanitos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.producto;

@Repository
public interface productoRepository extends JpaRepository<producto, Long> {

    Integer countByCategoria(String categoria);

    Integer countByTalla(String talla);

    Integer countByColor(String color);

}
