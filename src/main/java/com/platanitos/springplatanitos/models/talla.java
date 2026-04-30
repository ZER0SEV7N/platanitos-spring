package com.platanitos.springplatanitos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "talla")
public class Talla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String talla;

    public Talla() { }

    public Long getId() { return id; }   
    public void setId(Long id) { this.id = id; }

    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

}
