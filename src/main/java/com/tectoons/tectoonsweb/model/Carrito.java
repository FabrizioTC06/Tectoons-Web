package com.tectoons.tectoonsweb.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Carrito {

    private List<ItemCarrito> items = new ArrayList<>();

    public Double getTotal() {
        return items.stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
    }
}
