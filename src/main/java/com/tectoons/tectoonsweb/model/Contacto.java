package com.tectoons.tectoonsweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "contactos")
@Getter
@Setter
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{contacto.nombre.notblank}")
    @Size(min = 3, max = 100, message = "{contacto.nombre.size}")
    private String nombre;

    @NotBlank(message = "{contacto.email.notblank}")
    @Email(message = "{contacto.email.email}")
    @Size(max = 120, message = "{contacto.email.size}")
    private String email;

    @NotBlank(message = "{contacto.asunto.notblank}")
    @Size(min = 5, max = 200, message = "{contacto.asunto.size}")
    private String asunto;

    @NotBlank(message = "{contacto.mensaje.notblank}")
    @Size(min = 10, max = 1000, message = "{contacto.mensaje.size}")
    private String mensaje;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio = LocalDateTime.now();
}
