package com.tectoons.tectoonsweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter @Setter
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ⚡ Cambiado a Long

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;
}
