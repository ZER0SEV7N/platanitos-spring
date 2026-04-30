package com.platanitos.springplatanitos.models.dto;

import java.util.List;

public class CarritoDTO {

    private Long idUsuario;
    private List<ItemCarritoDTO> items;

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public List<ItemCarritoDTO> getItems() { return items; }
    public void setItems(List<ItemCarritoDTO> items) { this.items = items; }

    //Clase interna para representar los detalles de cada producto en el carrito
    public static class ItemCarritoDTO {
        private Long idVariante;
        private Integer cantidad;

        public Long getIdVariante() { return idVariante; }
        public void setIdVariante(Long idVariante) { this.idVariante = idVariante; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}
