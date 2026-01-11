package com.tectoons.tectoonsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

// Clase que representa una obra de arte
class Obra {
    private String titulo;
    private String autor;
    private String tipo; // "imagen" o "video"
    private String imagenUrl;
    private String videoUrl;
    private String categoria;

    // Constructor
    public Obra(String titulo, String autor, String tipo, String imagenUrl, String videoUrl, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.tipo = tipo;
        this.imagenUrl = imagenUrl;
        this.videoUrl = videoUrl;
        this.categoria = categoria;
    }

    // Getters y setters
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getTipo() { return tipo; }
    public String getImagenUrl() { return imagenUrl; }
    public String getVideoUrl() { return videoUrl; }
    public String getCategoria() { return categoria; }
}

@Controller
public class PortafolioController {

    @GetMapping("/portafolio1")
    public String mostrarPortafolio(Model model) {
        List<Obra> portafolios = new ArrayList<>();

        // Datos de ejemplo con imágenes de Picsum
        portafolios.add(new Obra("Paisaje Fantástico", "Alice", "imagen",
                "https://picsum.photos/id/1015/400/400", null, "Ilustración"));
        portafolios.add(new Obra("Personaje Estilizado", "Bob", "imagen",
                "https://picsum.photos/id/1025/400/400", null, "Personajes"));
        portafolios.add(new Obra("Animación Robot", "Charlie", "video",
                null, "https://www.w3schools.com/html/mov_bbb.mp4", "Animación"));
        portafolios.add(new Obra("Escenario Urbano", "Diana", "imagen",
                "https://picsum.photos/id/1035/400/400", null, "Escenarios"));
        portafolios.add(new Obra("Ilustración Abstracta", "Eve", "imagen",
                "https://picsum.photos/id/1045/400/400", null, "Estilizado"));
        portafolios.add(new Obra("Animación Personaje", "Frank", "video",
                null, "https://www.w3schools.com/html/movie.mp4", "Animación"));

        // Agregar más obras si deseas hasta 12 o más para probar la paginación
        for (int i = 0; i < 6; i++) {
            portafolios.add(new Obra("Obra Extra " + (i+1), "Autor " + (i+1), "imagen",
                    "https://picsum.photos/400/400?random=" + (i+10), null, "Ilustración"));
        }

        // Agregamos la lista al modelo
        model.addAttribute("portafolios", portafolios);

        return "portafolio"; // Thymeleaf buscará portafolio.html
    }
}
