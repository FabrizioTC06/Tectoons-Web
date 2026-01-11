package com.tectoons.tectoonsweb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "obra_id"}))
@Getter @Setter
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha = LocalDateTime.now();
}

