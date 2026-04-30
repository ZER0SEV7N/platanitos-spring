package com.platanitos.springplatanitos.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
@JsonPropertyOrder({"id", "producto", "descripcion", "categoria", "estado"})
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String producto;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idcategoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //Evita la referencia circular al serializar a JSON
    private java.util.List<ProductoVariante> variantes;

    @Column(nullable = false)
    private Boolean estado = true;

    public Producto() { }

    public Long getId() { return id; }   
    public void setId(Long id) { this.id = id; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public java.util.List<ProductoVariante> getVariantes() { return variantes; }
    public void setVariantes(java.util.List<ProductoVariante> variantes) { this.variantes = variantes; }

}
