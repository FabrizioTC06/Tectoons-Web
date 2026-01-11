package com.tectoons.tectoonsweb.repository;

import com.tectoons.tectoonsweb.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    // Buscar artistas por nombre artístico (para búsquedas parciales)
    List<Artista> findByNombreArtisticoContainingIgnoreCase(String nombreArtistico);

    // Buscar artista por el ID del usuario asociado
    Optional<Artista> findByUsuarioId(Long usuarioId);

    // Método comentado por ahora, puede ser útil para filtrar por especialidad
    // List<Artista> findByEspecialidadContainingIgnoreCase(String especialidad);
}
