package com.tectoons.tectoonsweb.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "artistas")
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "obras", "productos", "posts", "seguidores" })
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Usuario usuario;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nombre_artistico", nullable = false)
    private String nombreArtistico;

    // Categoría principal (obligatoria)
    @NotBlank
    @Size(max = 100)
    @Column(name = "categoria_principal", nullable = false)
    private String categoriaPrincipal;

    // Biografía completa
    @Lob
    private String biografiaLarga;

    // Redes sociales
    @Column(length = 255)
    private String instagram;

    @Column(length = 255)
    private String tiktok;

    @Column(length = 255)
    private String x; // Twitter/X

    @Column(length = 255)
    private String youtube;

    // Portafolio externo (Behance, ArtStation, Notion, etc.)
    @Column(name = "portafolio_url", length = 255)
    private String portafolioUrl;

    // Website personal del artista
    @Column(name = "sitio_web", length = 255)
    private String sitioWeb;

    // Fecha en que se convirtió en artista
    @Column(name = "fecha_creacion_rol")
    private LocalDateTime fechaCreacionRol = LocalDateTime.now();

    /*
     * @OneToMany(mappedBy = "artista", fetch = FetchType.LAZY, cascade =
     * CascadeType.ALL)
     * private List<Obra> obras;
     */
    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Obra> obras;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Producto> productos;

    // NUEVO: lista de seguidores
    @OneToMany(mappedBy = "seguido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({ "seguido" })
    private List<Seguidor> seguidores;

    // Relación posts
    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Post> posts;

}
