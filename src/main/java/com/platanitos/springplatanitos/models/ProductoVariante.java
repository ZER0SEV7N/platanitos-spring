package com.platanitos.springplatanitos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "producto_variante")
public class ProductoVariante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idproducto", nullable = false)
    @JsonBackReference //Evita la referencia circular al serializar a JSON
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idcolor", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "idtalla", nullable = false)
    private Talla talla;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Boolean estado = true;

    public ProductoVariante() { }

    public Long getId() { return id;}
    public void setId(Long id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public Talla getTalla() { return talla; }
    public void setTalla(Talla talla) { this.talla = talla; }

    public Integer getStock() { return stock;}
    public void setStock(Integer stock) { this.stock = stock; }

    public Double getPrecio() { return precio;}
    public void setPrecio(Double precio) { this.precio = precio;}

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}
