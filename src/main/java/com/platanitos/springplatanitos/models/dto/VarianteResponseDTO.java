package com.platanitos.springplatanitos.models.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "idProducto", "nombreProducto", "color", "talla", "precio", "stock", "estado"})
public class VarianteResponseDTO {
    private Long id;
    private Long idProducto;
    private String nombreProducto;
    private String color;
    private String talla;
    private Integer stock;
    private Double precio;
    private Boolean estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}
