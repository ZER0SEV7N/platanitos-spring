package com.platanitos.springplatanitos.repository.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.Talla;

@Repository
public interface TallaRepository extends JpaRepository<Talla, Long> {

    Integer countByTalla(String talla);

}
