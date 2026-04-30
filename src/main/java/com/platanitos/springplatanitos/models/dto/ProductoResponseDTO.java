package com.platanitos.springplatanitos.models.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "producto", "descripcion", "categoria", "variantes"})
public class ProductoResponseDTO {
    
    private Long id;
    private String producto;
    private String descripcion;
    private String categoria;
    private List<VarianteDTO> variantes;

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
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public List<VarianteDTO> getVariantes() {
        return variantes;
    }
    public void setVariantes(List<VarianteDTO> variantes) {
        this.variantes = variantes;
    }

    //Clase estatica interna para representar las variantes del producto
    @JsonPropertyOrder({"idVariante", "color", "talla", "precio", "stock"})
    public static class VarianteDTO{
        private Long idVariante;
        private String color;
        private String talla;
        private Double precio;
        private Integer stock;

        public Long getIdVariante() {
            return idVariante;
        }
        public void setIdVariante(Long idVariante) {
            this.idVariante = idVariante;
        }
        public String getColor() {
            return color;
        }
        public void setColor(String color) {
            this.color = color;
        }
        public String getTalla() {
            return talla;
        }
        public void setTalla(String talla) {
            this.talla = talla;
        }
        public Double getPrecio() {
            return precio;
        }
        public void setPrecio(Double precio) {
            this.precio = precio;
        }
        public Integer getStock() {
            return stock;
        }
        public void setStock(Integer stock) {
            this.stock = stock;
        }
    }
}
