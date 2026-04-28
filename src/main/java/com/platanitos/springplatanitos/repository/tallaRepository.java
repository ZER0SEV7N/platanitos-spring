package com.platanitos.springplatanitos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.talla;

@Repository
public interface tallaRepository extends JpaRepository<talla, Long> {

    Integer countByTalla(String talla);

}
