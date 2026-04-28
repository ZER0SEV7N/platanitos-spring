package com.platanitos.springplatanitos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.color;

@Repository
public interface colorRepository extends JpaRepository<color, Long> {

    Integer countByColor(String color);

}
