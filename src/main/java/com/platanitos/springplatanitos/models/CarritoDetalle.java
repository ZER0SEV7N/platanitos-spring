package com.platanitos.springplatanitos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "carrito_detalle")
public class CarritoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idcarrito", nullable = false)
    @JsonBackReference
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "idproductovariante", nullable = false)
    private ProductoVariante productoVariante;

    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(nullable = false)
    private Double subtotal;

    public Long getId() { return id; }   
    public void setId(Long id) { this.id = id; }

    public Carrito getCarrito() { return carrito; }
    public void setCarrito(Carrito carrito) { this.carrito = carrito; }

    public ProductoVariante getProductoVariante() { return productoVariante;}
    public void setProductoVariante(ProductoVariante productoVariante) { this.productoVariante = productoVariante;}

    public Integer getCantidad() { return cantidad;}
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}
