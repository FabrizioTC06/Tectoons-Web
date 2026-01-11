package com.tectoons.tectoonsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tectoons.tectoonsweb.service.CarritoService;

@Controller
@RequestMapping("/carrito")
@PreAuthorize("hasRole('USUARIO')")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/agregar/{id}")
    public String agregar(@PathVariable Long id) {
        carritoService.agregarProducto(id);
        return "redirect:/carrito";
    }

    @GetMapping
    public String verCarrito(Model model) {
        model.addAttribute("carrito", carritoService.getCarrito());
        return "carrito/index";
    }

    @PostMapping("/aumentar/{id}")
    public String aumentar(@PathVariable Long id) {
        carritoService.aumentarCantidad(id);
        return "redirect:/carrito";
    }

    @PostMapping("/disminuir/{id}")
    public String disminuir(@PathVariable Long id) {
        carritoService.disminuirCantidad(id);
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        carritoService.eliminarItem(id);
        return "redirect:/carrito";
    }
}
