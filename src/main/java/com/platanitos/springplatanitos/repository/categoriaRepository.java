package com.platanitos.springplatanitos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.categoria;

@Repository
public interface categoriaRepository extends JpaRepository<categoria, Long> {

    Integer countByCategoria(String categoria);

}
