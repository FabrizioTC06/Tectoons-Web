package com.tectoons.tectoonsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tectoons.tectoonsweb.model.Pedido;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.CarritoService;
import com.tectoons.tectoonsweb.service.PedidoService;

@Controller
@RequestMapping("/carrito")
@PreAuthorize("hasRole('USUARIO')")
public class PagoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/pagar")
    public String procesarPago(@AuthenticationPrincipal CustomUserDetails user, Model model) {

        if (user == null) return "redirect:/usuario/login";

        if (carritoService.getCarrito().getItems().isEmpty())
            return "redirect:/carrito?empty";

        Usuario usuario = user.getUsuario();

        try {
            Pedido pedido = pedidoService.crearPedidoDesdeCarrito(usuario, carritoService.getCarrito());
            carritoService.vaciar();
            return "redirect:/carrito/boleta/" + pedido.getId();
        } catch (RuntimeException e) {
            model.addAttribute("errorStock", e.getMessage());
            model.addAttribute("carrito", carritoService.getCarrito());
            return "carrito/index";
        }
    }

    @GetMapping("/boleta/{id}")
    public String verBoleta(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.obtenerPorId(id);

        if (pedido == null) {
            return "redirect:/tienda";
        }

        model.addAttribute("pedido", pedido);
        return "carrito/boleta";
    }
}
