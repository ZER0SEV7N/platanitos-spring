package com.platanitos.springplatanitos.repository.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.Producto;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    //Consulta personalizada para filtrar por categoria 
    List<Producto> findByCategoria_Categoria(String categoria);

    //Consulta personalizada para filtrar el estado del producto
    List<Producto> findAllByEstadoTrue();

}
