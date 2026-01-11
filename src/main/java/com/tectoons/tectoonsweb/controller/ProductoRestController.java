package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Producto;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.ArtistaService;
import com.tectoons.tectoonsweb.service.ProductoService;
import com.tectoons.tectoonsweb.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artista/productos")
@PreAuthorize("hasRole('ARTISTA')")
public class ProductoRestController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private UsuarioService usuarioService;

    // ======================== MÉTODO AUXILIAR ========================
    private Usuario getUsuarioLogueado(CustomUserDetails userDetails) {
        return usuarioService.buscarPorId(userDetails.getId()).orElseThrow();
    }

    private Artista getArtistaLogueado(CustomUserDetails userDetails) {
        Usuario usuario = getUsuarioLogueado(userDetails);
        return artistaService.obtenerPorUsuarioId(usuario.getId()).orElseThrow();
    }

    // ======================== CRUD ========================
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Artista artista = getArtistaLogueado(userDetails);
        return ResponseEntity.ok(artista.getProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id,
                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        Producto producto = productoService.obtenerPorId(id);
        if (producto == null || !producto.getArtista().getUsuario().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        Artista artista = getArtistaLogueado(userDetails);
        producto.setArtista(artista);
        Producto guardado = productoService.guardar(producto);
        return ResponseEntity.status(201).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id,
                                                       @RequestBody Producto producto,
                                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        Producto existente = productoService.obtenerPorId(id);
        if (existente == null || !existente.getArtista().getUsuario().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(403).build();
        }

        existente.setTitulo(producto.getTitulo());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecio(producto.getPrecio());
        existente.setStock(producto.getStock());
        existente.setCategoria(producto.getCategoria());
        existente.setImagenUrl(producto.getImagenUrl());

        Producto actualizado = productoService.guardar(existente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        Producto producto = productoService.obtenerPorId(id);
        if (producto == null || !producto.getArtista().getUsuario().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(403).build();
        }
        productoService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
