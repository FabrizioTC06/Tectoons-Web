package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.EstadoSolicitudArtista;
import com.tectoons.tectoonsweb.service.SolicitudArtistaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/solicitudes-artista")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSolicitudArtistaController {

    @Autowired
    private SolicitudArtistaService solicitudService;

    // ================================================
    // Listado general
    // ================================================
    @GetMapping
    public String listarSolicitudes(Model model) {
        model.addAttribute("solicitudes", solicitudService.listarTodas());
        return "admin/solicitudes/lista";
    }

    // ================================================
    // Listar por estado
    // ================================================
    @GetMapping("/estado/{estado}")
    public String listarPorEstado(@PathVariable EstadoSolicitudArtista estado, Model model) {
        model.addAttribute("solicitudes", solicitudService.listarPorEstado(estado));
        model.addAttribute("estadoActual", estado);
        return "admin/solicitudes/lista";
    }

    // ================================================
    // Aprobar
    // ================================================
    @PostMapping("/{id}/aprobar")
    public String aprobar(@PathVariable Long id) {
        solicitudService.aprobarSolicitud(id);
        return "redirect:/admin/solicitudes-artista?exito=Aprobada";
    }

    // ================================================
    // Rechazar
    // ================================================
    @PostMapping("/{id}/rechazar")
    public String rechazar(@PathVariable Long id) {
        solicitudService.rechazarSolicitud(id);
        return "redirect:/admin/solicitudes-artista?exito=Rechazada";
    }
}
