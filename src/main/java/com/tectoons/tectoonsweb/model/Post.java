package com.tectoons.tectoonsweb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Para saber qué artista creó el dibujo/post
    @ManyToOne
    @JoinColumn(name = "artista_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "posts" })
    private Artista artista;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String contenido; // Aquí va tu descripción o proceso creativo

    // Ruta del archivo subido (imagen del dibujo)
    private String imagenUrl;

    private LocalDateTime fechaPublicacion = LocalDateTime.now();
}