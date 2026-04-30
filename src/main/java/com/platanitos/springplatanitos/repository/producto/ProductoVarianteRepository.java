package com.platanitos.springplatanitos.repository.producto;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.ProductoVariante;

@Repository
public interface ProductoVarianteRepository extends JpaRepository<ProductoVariante, Long>{

    //Consulta personalizada para obtener todas las variantes de un producto que esten activas
    List<ProductoVariante> findByProductoIdAndEstadoTrue(Long idProducto);

    //Consulta personalizada para obtener todas las variantes que esten activas
    List<ProductoVariante> findAllByEstadoTrue();

    //Consulta personalizada para contar el numero de variantes que existen con una talla y color especificos
    Integer countByTalla_TallaAndColor_ColorAndProductoId(String talla, String color, Long idProducto);
}
