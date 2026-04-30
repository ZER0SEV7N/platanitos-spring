package com.platanitos.springplatanitos.models.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"idCarrito", "total", "detalles"})
public class CarritoResponseDTO {
    private Long idCarrito;
    private Double total;
    private List<DetalleCarritoDTO> detalles;

    public Long getIdCarrito() { return idCarrito; }
    public void setIdCarrito(Long idCarrito) { this.idCarrito = idCarrito; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public List<DetalleCarritoDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleCarritoDTO> detalles) { this.detalles = detalles; }

    //Clase interna para representar la vista plana de cada ítem
    @JsonPropertyOrder({"idCarritoDetalle", "idVariante", "producto", "color", "talla", "cantidad", "subtotal"})
    public static class DetalleCarritoDTO {
        private Long idCarritoDetalle; //Útil para cuando el frontend quiera eliminar un ítem
        private Long idVariante;
        private String producto; //Ejemplo: "Zapatillas Urbanas"
        private String color;    //Ejemplo: "negro"
        private String talla;    //Ejemplo: "m"
        private Integer cantidad;
        private Double subtotal;

        public Long getIdCarritoDetalle() { return idCarritoDetalle; }
        public void setIdCarritoDetalle(Long idCarritoDetalle) { this.idCarritoDetalle = idCarritoDetalle; }

        public Long getIdVariante() { return idVariante; }
        public void setIdVariante(Long idVariante) { this.idVariante = idVariante; }

        public String getProducto() { return producto; }
        public void setProducto(String producto) { this.producto = producto; }

        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }

        public String getTalla() { return talla; }
        public void setTalla(String talla) { this.talla = talla; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

        public Double getSubtotal() { return subtotal; }
        public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    }
}
