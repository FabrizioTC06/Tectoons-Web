package com.tectoons.tectoonsweb.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemCarrito {

    private Producto producto;
    private Integer cantidad;

    public ItemCarrito(Producto producto) {
        this.producto = producto;
        this.cantidad = 1;
    }

    public Double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}
