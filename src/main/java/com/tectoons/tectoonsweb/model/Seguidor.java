package com.tectoons.tectoonsweb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguidores", uniqueConstraints = @UniqueConstraint(columnNames = { "seguidor_id", "seguido_id" }))
@Getter
@Setter
public class Seguidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seguidor_id", nullable = false)
    private Usuario seguidor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seguido_id", nullable = false)
    private Artista seguido;

    /*
     * @Column(nullable = false)
     * private LocalDateTime fecha = LocalDateTime.now();
     */
    @Column(name = "fecha")
    private LocalDateTime fecha = LocalDateTime.now();

}
