/*package com.tectoons.tectoonsweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "usuarios")
@Getter @Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ⚡ Cambiado a Long

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 15)
    private String celular;

    private String fotoPerfil;
    private String bannerPerfil;

    private boolean activo = true;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now(); // ⚡ Se inicializa automáticamente

    // Relación muchos a muchos con roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    public void addRol(Rol rol) {
    this.roles.add(rol);
}

}*/

package com.tectoons.tectoonsweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * @NotBlank(message = "El DNI es obligatorio")
     * 
     * @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
     * 
     * @Column(nullable = false, unique = true, length = 8)
     * private String dni;
     */

    @NotBlank(message = "El nombre completo es obligatorio")
    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    private boolean activo = true;

    /*
     * @Column(name = "fecha_nacimiento")
     * private LocalDate fechaNacimiento;
     */

    @Column(name = "fecha_nacimiento")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "banner_perfil")
    private String bannerPerfil;

    @Column(length = 100)
    private String ubicacion;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    // Relación muchos a muchos con roles

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    /*
     * @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
     * private Set<Like> likes;
     */
    /*
     * @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
     * private Set<Like> likes;
     */

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();

    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Artista artista;

    /*
     * public void addRol(Rol rol) {
     * this.roles.add(rol);
     * }
     */
}
