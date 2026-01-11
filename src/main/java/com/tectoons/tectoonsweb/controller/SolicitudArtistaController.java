package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.SolicitudArtista;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.ArtistaService;
import com.tectoons.tectoonsweb.service.SolicitudArtistaService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/solicitud-artista")
public class SolicitudArtistaController {

    @Autowired
    private SolicitudArtistaService solicitudService;

    @Autowired
    private ArtistaService artistaService;

    // ================================================
    // GET - Mostrar formulario
    // ================================================
    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping
    public String mostrarFormulario(
            @AuthenticationPrincipal CustomUserDetails usuarioDetails,
            Model model) {

        boolean esArtista = artistaService.obtenerPorUsuarioId(usuarioDetails.getId()).isPresent();

        model.addAttribute("solicitud", new SolicitudArtista());
        model.addAttribute("esArtista", esArtista); // indicador para Thymeleaf

        return "solicitud-artista/form";
    }

    // ================================================
    // POST - Enviar solicitud
    // ================================================
    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping
    public String enviarSolicitud(
            @Valid @ModelAttribute("solicitud") SolicitudArtista solicitud,
            @AuthenticationPrincipal CustomUserDetails usuarioDetails,
            Model model) {

        boolean esArtista = artistaService.obtenerPorUsuarioId(usuarioDetails.getId()).isPresent();

        if (esArtista) {
            model.addAttribute("esArtista", true);
            model.addAttribute("error", "Ya eres artista, no puedes enviar otra solicitud.");
            return "solicitud-artista/form";
        }

        try {
            solicitudService.crearSolicitud(usuarioDetails.getId(), solicitud);
            return "redirect:/solicitud-artista/confirmacion";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "solicitud-artista/form";
        }
    }

    // ================================================
    // GET - Confirmación
    // ================================================
    @GetMapping("/confirmacion")
    public String confirmacion() {
        return "solicitud-artista/confirmacion";
    }

    // ================================================
    // GET - Ver mis solicitudes
    // ================================================
    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("/mis-solicitudes")
    public String verMisSolicitudes(
            @AuthenticationPrincipal CustomUserDetails usuarioDetails,
            Model model) {

        model.addAttribute("solicitudes",
                solicitudService.solicitudesPorUsuario(usuarioDetails.getId()));

        return "solicitud-artista/mis-solicitudes";
    }
}
