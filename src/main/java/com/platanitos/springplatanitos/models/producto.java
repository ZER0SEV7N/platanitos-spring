package com.platanitos.springplatanitos.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
@JsonPropertyOrder({"id", "producto", "descripcion", "precio", "stock", "color", "talla", "categoria", "estado"})
public class producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String producto;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "idcolor", nullable = false)
    private color color;

    @ManyToOne
    @JoinColumn(name = "idtalla", nullable = false)
    private talla talla;

    @ManyToOne
    @JoinColumn(name = "idcategoria", nullable = false)
    private categoria categoria;

    @Column(nullable = false)
    private Boolean estado = true;

    public producto() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public color getColor() {
        return color;
    }

    public void setColor(color color) {
        this.color = color;
    }

    public talla getTalla() {
        return talla;
    }

    public void setTalla(talla talla) {
        this.talla = talla;
    }

    public categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
