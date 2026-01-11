package com.tectoons.tectoonsweb.repository;

import com.tectoons.tectoonsweb.model.SolicitudArtista;
import com.tectoons.tectoonsweb.model.EstadoSolicitudArtista;
import com.tectoons.tectoonsweb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitudArtistaRepository extends JpaRepository<SolicitudArtista, Long> {

    // Verifica si un usuario ya envió una solicitud pendiente
    boolean existsByUsuarioAndEstado(Usuario usuario, EstadoSolicitudArtista estado);

    // Obtiene solicitudes de un usuario
    List<SolicitudArtista> findByUsuario(Usuario usuario);

    // Busca solicitudes por estado
    List<SolicitudArtista> findByEstado(EstadoSolicitudArtista estado);

    // Para evitar duplicados
    Optional<SolicitudArtista> findByUsuarioAndEstado(Usuario usuario, EstadoSolicitudArtista estado);
}
