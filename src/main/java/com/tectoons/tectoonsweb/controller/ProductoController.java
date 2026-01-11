package com.tectoons.tectoonsweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Producto;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.ProductoService;
import com.tectoons.tectoonsweb.service.UsuarioService;

@Controller
@RequestMapping
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    // ======================== TIENDA PÚBLICA ========================
    @GetMapping("/tienda")
    public String verTienda(@RequestParam(required = false) String categoria, Model model) {
        List<Producto> productos = (categoria != null && !categoria.isEmpty())
                ? productoService.filtrarPorCategoria(categoria)
                : productoService.listarTodos();

        model.addAttribute("productos", productos);
        model.addAttribute("categoria", categoria);

        return "tienda/index";
    }

    @GetMapping("/tienda/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        if (producto == null) return "redirect:/tienda";

        model.addAttribute("producto", producto);
        model.addAttribute("logueado", getUsuarioLogueado() != null);

        return "tienda/detalle-producto";
    }

    // ======================== CRUD PRODUCTOS DEL ARTISTA ========================
    @PreAuthorize("hasRole('ARTISTA')")
    @GetMapping("/artista/mis-productos")
    public String mostrarCrudProductos(Model model) {
        Artista artista = getArtistaLogueado();
        if (artista == null) throw new RuntimeException("Artista no encontrado");

        model.addAttribute("artista", artista);
        model.addAttribute("productos", artista.getProductos());

        return "artista/productos-crud"; // Thymeleaf
    }

    // ======================== MÉTODOS AUXILIARES ========================
    private Usuario getUsuarioLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return usuarioService.buscarPorId(userDetails.getId()).orElse(null);
    }

    private Artista getArtistaLogueado() {
        Usuario usuario = getUsuarioLogueado();
        return (usuario != null) ? usuario.getArtista() : null;
    }
}
