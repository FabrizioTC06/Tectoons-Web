package com.tectoons.tectoonsweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "obras")
@Getter
@Setter
@JsonIgnoreProperties({ "obras" })
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * @ManyToOne
     * 
     * @JoinColumn(name = "artista_id", nullable = false)
     * private Artista artista;
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false)
    @JsonBackReference
    private Artista artista;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String titulo;

    @Size(max = 5000, message = "La descripción no puede superar los 5000 caracteres")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String imagenUrl;
    private String videoUrl;

    /*
     * @Temporal(TemporalType.TIMESTAMP)
     * private Date fechaPublicacion = new Date();
     */

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion = LocalDateTime.now();

    /*
     * private int likes = 0;
     */
    @Transient
    private Long totalLikes;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    /*
     * @ManyToMany(fetch = FetchType.LAZY)
     * 
     * @JoinTable(name = "obras_categorias", joinColumns = @JoinColumn(name =
     * "obra_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
     * private Set<Categoria> categorias = new HashSet<>();
     */
}
