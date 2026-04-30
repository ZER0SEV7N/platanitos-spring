package com.platanitos.springplatanitos.repository.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.platanitos.springplatanitos.models.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

    Integer countByColor(String color);

}
