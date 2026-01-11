package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    // LISTA PÚBLICA DE POSTS (BD)
    @GetMapping("")
    public String listarPublicamente(Model model) {
        model.addAttribute("posts", postService.listarTodos());
        return "public-blog"; // /templates/blog/public-list.html
    }

    // DETALLE DE POST PARA EL PÚBLICO (BD)
    @GetMapping("/{id}")
    public String verPostPublico(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.obtenerPorId(id));
        return "public-blog-detail"; // /templates/blog/public-detail.html
    }
}