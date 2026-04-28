package com.platanitos.springplatanitos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platanitos.springplatanitos.models.usuario;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Long> {
    usuario findByEmail(String email);
}
