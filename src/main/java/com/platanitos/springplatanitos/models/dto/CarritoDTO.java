package com.platanitos.springplatanitos.models.dto;

import java.util.List;

public class CarritoDTO {

    private Long idUsuario;
    private List<ItemCarritoDTO> Items;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public List<ItemCarritoDTO> getItems() { return Items; }
    public void setItems(List<ItemCarritoDTO> items) { Items = items; }

    //Clase interna para representar los detalles de cada producto en el carrito
    public static class ItemCarritoDTO {
        private Long idProducto;
        private Integer cantidad;

        public Long getIdProducto() { return idProducto; }
        public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}
