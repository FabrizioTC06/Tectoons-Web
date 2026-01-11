package com.tectoons.tectoonsweb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "solicitudes_artista")
@Getter
@Setter
public class SolicitudArtista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuario que envía la solicitud
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Nombre artístico propuesto
    @NotBlank(message = "El nombre artístico es obligatorio")
    @Size(max = 100)
    @Column(name = "nombre_artistico", nullable = false)
    private String nombreArtistico;

    // Categoría principal
    @NotBlank(message = "La categoría principal es obligatoria")
    @Size(max = 100)
    @Column(name = "categoria_principal", nullable = false)
    private String categoriaPrincipal;

    // Breve descripción o motivo de la solicitud
    @Lob
    @NotBlank(message = "Debes explicar por qué deseas ser artista")
    @Size(max = 5000)
    @Column(name = "motivo", nullable = false)
    private String motivo;

    // Evidencia inicial que el admin revisa (Behance, Drive, PDF, carpeta, etc.)
    @Column(name = "evidencia_url", length = 255)
    private String evidenciaUrl;

    // Estado de la solicitud
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitudArtista estado = EstadoSolicitudArtista.PENDIENTE;

    // Fecha en que la envió
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    // Fecha de aprobación/rechazo
    @Column(name = "fecha_respuesta")
    private LocalDateTime fechaRespuesta;

    // Admin que revisó la solicitud
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "admin_id")
    private Usuario adminRevisor;

    // Comentario del admin (opcional)
    @Column(columnDefinition = "TEXT")
    private String comentarioAdmin;
}
