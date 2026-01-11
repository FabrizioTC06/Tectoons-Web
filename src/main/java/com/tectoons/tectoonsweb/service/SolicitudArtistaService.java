package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.*;
import com.tectoons.tectoonsweb.repository.SolicitudArtistaRepository;
import com.tectoons.tectoonsweb.repository.ArtistaRepository;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitudArtistaService {

    @Autowired
    private SolicitudArtistaRepository solicitudArtistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private UsuarioService usuarioService;

    // ========================================================
    // CREAR SOLICITUD DE ARTISTA
    // ========================================================
    @Transactional
    public void crearSolicitud(Long usuarioId, SolicitudArtista datos) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar que NO tenga una solicitud pendiente
        if (solicitudArtistaRepository.existsByUsuarioAndEstado(usuario, EstadoSolicitudArtista.PENDIENTE)) {
            throw new RuntimeException("Ya tienes una solicitud pendiente.");
        }

        SolicitudArtista solicitud = new SolicitudArtista();
        solicitud.setUsuario(usuario);
        solicitud.setNombreArtistico(datos.getNombreArtistico());
        solicitud.setCategoriaPrincipal(datos.getCategoriaPrincipal());
        solicitud.setMotivo(datos.getMotivo());
        solicitud.setEvidenciaUrl(datos.getEvidenciaUrl());
        solicitud.setEstado(EstadoSolicitudArtista.PENDIENTE);
        solicitud.setFechaSolicitud(LocalDateTime.now());

        solicitudArtistaRepository.save(solicitud);
    }

    // ========================================================
    // OBTENER TODAS LAS SOLICITUDES PARA ADMIN
    // ========================================================
    public List<SolicitudArtista> listarTodas() {
        return solicitudArtistaRepository.findAll();
    }

    public List<SolicitudArtista> listarPorEstado(EstadoSolicitudArtista estado) {
        return solicitudArtistaRepository.findByEstado(estado);
    }

    // ========================================================
    // APROBAR SOLICITUD
    // ========================================================
    @Transactional
    public void aprobarSolicitud(Long solicitudId) {

        SolicitudArtista solicitud = solicitudArtistaRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Usuario usuario = solicitud.getUsuario();

        // Crear perfil de artista
        Artista artista = new Artista();
        artista.setUsuario(usuario);
        artista.setNombreArtistico(solicitud.getNombreArtistico());
        artista.setCategoriaPrincipal(solicitud.getCategoriaPrincipal());
        //artista.setBiografiaLarga(solicitud.getMotivo());
        artista.setFechaCreacionRol(LocalDateTime.now());

        artistaRepository.save(artista);

        // Asignar rol ROLE_ARTISTA
        usuarioService.asignarRolArtista(usuario.getId());

        // Actualizar estado
        solicitud.setEstado(EstadoSolicitudArtista.APROBADA);
        solicitudArtistaRepository.save(solicitud);
    }

    // ========================================================
    // RECHAZAR SOLICITUD
    // ========================================================
    @Transactional
    public void rechazarSolicitud(Long solicitudId) {

        SolicitudArtista solicitud = solicitudArtistaRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitudArtista.RECHAZADA);
        solicitudArtistaRepository.save(solicitud);
    }

    // ========================================================
    // OBTENER SOLICITUDES DE UN USUARIO
    // ========================================================
    public List<SolicitudArtista> solicitudesPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return solicitudArtistaRepository.findByUsuario(usuario);
    }
}
