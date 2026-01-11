package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tectoons.tectoonsweb.model.Carrito;
import com.tectoons.tectoonsweb.model.ItemCarrito;
import com.tectoons.tectoonsweb.model.Pedido;
import com.tectoons.tectoonsweb.model.DetallePedido;
import com.tectoons.tectoonsweb.model.Producto;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.DetallePedidoRepository;
import com.tectoons.tectoonsweb.repository.PedidoRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ProductoService productoService;

    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }   

    @Transactional
    public Pedido crearPedidoDesdeCarrito(Usuario usuario, Carrito carrito) {

        // Calcular total real
        double total = carrito.getItems().stream()
                .mapToDouble(i -> i.getCantidad() * i.getProducto().getPrecio())
                .sum();

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setTotal(total);
        pedido = pedidoRepository.save(pedido);

        // Crear los detalles del pedido
        for (ItemCarrito item : carrito.getItems()) {

            Producto p = item.getProducto();

            if (p.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para " + p.getTitulo());
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(p);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(p.getPrecio());

            detallePedidoRepository.save(detalle);

            // Reducir stock
            p.setStock(p.getStock() - item.getCantidad());
            productoService.guardar(p);
        }

        return pedido;
    }
}
