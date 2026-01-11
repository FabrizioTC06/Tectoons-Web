/*package com.tectoons.tectoonsweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 50, message = "El nombre de la categoría no puede superar los 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @ManyToMany(mappedBy = "categorias")
    private Set<Obra> obras = new HashSet<>();

}
*/