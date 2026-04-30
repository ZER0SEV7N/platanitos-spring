package com.platanitos.springplatanitos.repository.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Integer countByCategoria(String categoria);

}
