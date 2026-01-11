package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.tectoons.tectoonsweb.model.Carrito;
import com.tectoons.tectoonsweb.model.ItemCarrito;
import com.tectoons.tectoonsweb.model.Producto;

@Service
@SessionScope
public class CarritoService {

    private Carrito carrito = new Carrito();

    @Autowired
    private ProductoService productoService;

    public Carrito getCarrito() {
        return carrito;
    }

    public void agregarProducto(Long productoId) {
        Producto producto = productoService.obtenerPorId(productoId);

        if (producto == null) return;

        for (ItemCarrito item : carrito.getItems()) {
            if (item.getProducto().getId().equals(productoId)) {
                item.setCantidad(item.getCantidad() + 1);
                return;
            }
        }

        carrito.getItems().add(new ItemCarrito(producto));
    }

    public void aumentarCantidad(Long productoId) {
        carrito.getItems().forEach(item -> {
            if (item.getProducto().getId().equals(productoId)) {
                item.setCantidad(item.getCantidad() + 1);
            }
        });
    }

    public void disminuirCantidad(Long productoId) {
        carrito.getItems().removeIf(item -> {
            if (item.getProducto().getId().equals(productoId)) {
                item.setCantidad(item.getCantidad() - 1);
                return item.getCantidad() <= 0;
            }
            return false;
        });
    }

    public void eliminarItem(Long productoId) {
        carrito.getItems().removeIf(item -> item.getProducto().getId().equals(productoId));
    }

    public void vaciar() {
        carrito.getItems().clear();
    }
}
