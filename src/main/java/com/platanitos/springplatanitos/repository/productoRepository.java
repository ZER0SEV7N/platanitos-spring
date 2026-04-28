package com.platanitos.springplatanitos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.producto;
import java.util.List;

@Repository
public interface productoRepository extends JpaRepository<producto, Long> {

    //Consulta personalizada para filtrar por categoria 
    List<producto> findByCategoria_Categoria(String categoria);

    //Consulta personalizada para filtrar el estado del producto
    List<producto> findAllByEstadoTrue();

}
