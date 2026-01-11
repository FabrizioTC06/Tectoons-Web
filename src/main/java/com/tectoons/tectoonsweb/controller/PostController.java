package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Post;
import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.ArtistaService;
import com.tectoons.tectoonsweb.service.PostService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/artista/mis-posts")
public class PostController {

    private final PostService postService;
    private final ArtistaService artistaService;

    public PostController(PostService postService, ArtistaService artistaService) {
        this.postService = postService;
        this.artistaService = artistaService;
    }

    // ============================
    // LISTAR POSTS DEL ARTISTA
    // ============================
    @GetMapping("")
    public String listarMisPosts(Model model,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long usuarioId = userDetails.getId();
        Artista artista = artistaService.obtenerPorUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se encontró artista"));

        model.addAttribute("posts", postService.listarPorArtista(artista.getId()));

        return "blog";
    }

    // ============================
    // FORMULARIO NUEVO POST
    // ============================
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("post", new Post());
        return "blog";
    }

    // ============================
    // GUARDAR POST (usa URL)
    // ============================
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute Post post,
            @RequestParam("imagenUrl") String imagenUrl,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long usuarioId = userDetails.getId();
        Artista artista = artistaService.obtenerPorUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        // Guardamos el link de la nube directamente
        if (imagenUrl != null && !imagenUrl.isBlank()) {
            post.setImagenUrl(imagenUrl.trim());
        }

        postService.crearPost(artista.getId(), post);
        return "redirect:/artista/mis-posts";
    }

    // ============================
    // FORMULARIO EDITAR
    // ============================
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.obtenerPorId(id));
        return "artista/posts/form";
    }

    // ============================
    // ACTUALIZAR POST (usa URL)
    // ============================
    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @ModelAttribute Post nuevosDatos,
            @RequestParam("imagenUrl") String imagenUrl) {

        Post postActual = postService.obtenerPorId(id);

        if (imagenUrl != null && !imagenUrl.isBlank()) {
            nuevosDatos.setImagenUrl(imagenUrl.trim());
        } else {
            nuevosDatos.setImagenUrl(postActual.getImagenUrl());
        }

        postService.editarPost(id, nuevosDatos);
        return "redirect:/artista/mis-posts";
    }

    // ============================
    // ELIMINAR POST
    // ============================
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        postService.eliminarPost(id);
        return "redirect:/artista/mis-posts";
    }

    // ============================
    // API PARA EDITAR (modal)
    // ============================
    @GetMapping("/api/mis-posts/{id}")
    @ResponseBody
    public Post obtenerPost(@PathVariable Long id) {
        return postService.obtenerPorId(id);
    }

}