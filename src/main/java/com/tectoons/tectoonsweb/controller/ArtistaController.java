/*package com.tectoons.tectoonsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;*/

/**
 * ArtistaController
 * - /artistas          -> page (Thymeleaf)
 * - /api/artistas      -> JSON list
 * - /artistas/{id}     -> page detalle (Thymeleaf)
 * - /api/artistas/{id} -> JSON detalle
 *
 * Mock data incluido para demo. Reemplaza por servicio/DAO para producción.
 */
/*@Controller
@RequestMapping("/artistas")
public class ArtistaController {

    // Simple DTOs (public getters para serialización JSON)
    public static class Obra {
        private final String titulo;
        private final String imagenUrl;
        private final String tipo; // "image" o "video"
        private final String videoUrl;

        public Obra(String titulo, String imagenUrl, String tipo, String videoUrl) {
            this.titulo = titulo;
            this.imagenUrl = imagenUrl;
            this.tipo = tipo;
            this.videoUrl = videoUrl;
        }

        public String getTitulo() { return titulo; }
        public String getImagenUrl() { return imagenUrl; }
        public String getTipo() { return tipo; }
        public String getVideoUrl() { return videoUrl; }
    }

    public static class Artista {
        private final Long id;
        private final String nombre;
        private final String bio;
        private final String avatar;
        private final String ubicacion;
        private final List<String> redes;
        private final List<Obra> galeria;

        public Artista(Long id, String nombre, String bio, String avatar, String ubicacion, List<String> redes, List<Obra> galeria) {
            this.id = id;
            this.nombre = nombre;
            this.bio = bio;
            this.avatar = avatar;
            this.ubicacion = ubicacion;
            this.redes = redes;
            this.galeria = galeria;
        }

        public Long getId() { return id; }
        public String getNombre() { return nombre; }
        public String getBio() { return bio; }
        public String getAvatar() { return avatar; }
        public String getUbicacion() { return ubicacion; }
        public List<String> getRedes() { return redes; }
        public List<Obra> getGaleria() { return galeria; }
    }

    // Mock dataset (inmutable)
    private final List<Artista> ARTISTAS = List.of(
        new Artista(1L, "Maya Rivera",
                "Ilustradora digital que fusiona lo orgánico con lo futurista. Trabaja con paletas vibrantes y narrativas visuales.",
                "https://picsum.photos/seed/maya/400/400",
                "Trujillo, Perú",
                List.of("https://instagram.com/mayarivera", "https://twitter.com/mayar"),
                List.of(
                    new Obra("Nebula Dreams", "https://picsum.photos/seed/nebula/1200/800", "image", null),
                    new Obra("Echoes Motion", "https://picsum.photos/seed/echo/1200/900", "image", null),
                    new Obra("Pulse Clip", null, "video", "https://www.youtube.com/embed/tgbNymZ7vqY")
                )
        ),
        new Artista(2L, "Leo Paredes",
                "Diseñador y pixel artist con énfasis en micro-narrativas y estética retro-futurista.",
                "https://picsum.photos/seed/leo/400/400",
                "Lima, Perú",
                List.of("https://instagram.com/leoparedes"),
                List.of(
                    new Obra("Pixel Garden", "https://picsum.photos/seed/pixel/1200/800", "image", null),
                    new Obra("Loop Sprite", null, "video", "https://www.youtube.com/embed/dQw4w9WgXcQ")
                )
        ),
        new Artista(3L, "Ariana Sol",
                "Artista tradicional y digital. Sus obras exploran paisajes emocionales y texturas manuales.",
                "https://picsum.photos/seed/ariana/400/400",
                "Arequipa, Perú",
                List.of("https://instagram.com/ariana"),
                List.of(
                    new Obra("Tide Study", "https://picsum.photos/seed/tide/1200/800", "image", null),
                    new Obra("Glacial Motion", "https://picsum.photos/seed/glacial/1200/800", "image", null)
                )
        )
    );

    // ---- Vistas Thymeleaf ----

    @GetMapping
    public String listaArtistas(Model model) {
        // Thymeleaf fallback (progressive enhancement). JS puede fetch /api/artistas para actualizar.
        model.addAttribute("artistas", ARTISTAS);
        model.addAttribute("tituloPagina", "Artistas | TecToons");
        return "artistas";
    }

    @GetMapping("/{id}")
    public String detalleArtista(@PathVariable Long id, Model model) {
        Optional<Artista> a = ARTISTAS.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (a.isEmpty()) {
            return "redirect:/artistas";
        }
        model.addAttribute("artista", a.get());
        model.addAttribute("tituloPagina", a.get().getNombre() + " | TecToons");
        return "detalle-artista";
    }

    // ---- API JSON ----

    @GetMapping("/api/artistas")
    @ResponseBody
    public List<Artista> apiListar() {
        return ARTISTAS;
    }

    @GetMapping("/api/artistas/{id}")
    @ResponseBody
    public Artista apiDetalle(@PathVariable Long id) {
        return ARTISTAS.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }
}
*/

/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artistas")
@CrossOrigin(origins = "*")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping
    public List<Artista> listarTodos() {
        return artistaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> obtenerPorId(@PathVariable Integer id) {
        Optional<Artista> artista = artistaService.obtenerPorId(id);
        return artista.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Artista> crear(@RequestBody Artista artista) {
        return ResponseEntity.ok(artistaService.guardar(artista));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artista> actualizar(@PathVariable Integer id, @RequestBody Artista artistaActualizado) {
        Optional<Artista> artistaExistente = artistaService.obtenerPorId(id);
        if (artistaExistente.isPresent()) {
            Artista artista = artistaExistente.get();
            artista.setNombreArtistico(artistaActualizado.getNombreArtistico());
            artista.setEspecialidad(artistaActualizado.getEspecialidad());
            artista.setUbicacion(artistaActualizado.getUbicacion());
            artista.setSitioWeb(artistaActualizado.getSitioWeb());
            return ResponseEntity.ok(artistaService.guardar(artista));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Artista> artista = artistaService.obtenerPorId(id);
        if (artista.isPresent()) {
            artistaService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar/especialidad/{especialidad}")
    public List<Artista> buscarPorEspecialidad(@PathVariable String especialidad) {
        return artistaService.buscarPorEspecialidad(especialidad);
    }

    @GetMapping("/buscar/nombre/{nombre}")
    public List<Artista> buscarPorNombreArtistico(@PathVariable String nombre) {
        return artistaService.buscarPorNombreArtistico(nombre);
    }
}
*/

/*package com.tectoons.tectoonsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.service.ArtistaService;
import java.util.List;

@Controller
@RequestMapping("/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping
    public String listarArtistas(Model model) {
        List<Artista> artistas = artistaService.listarTodos();
        model.addAttribute("artistas", artistas);
        return "artistas/listar";
    }

    @GetMapping("/detalle/{id}")
    public String detalleArtista(@PathVariable Long id, Model model) {
        artistaService.obtenerPorId(id).ifPresent(a -> model.addAttribute("artista", a));
        return "artistas/detalle-artista";
    }

    @GetMapping("/nuevo")
    public String nuevoArtista(Model model) {
        model.addAttribute("artista", new Artista());
        return "artistas/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarArtista(@ModelAttribute Artista artista) {
        artistaService.guardar(artista);
        return "redirect:/artistas";
    }

    @GetMapping("/editar/{id}")
    public String editarArtista(@PathVariable Long id, Model model) {
        artistaService.obtenerPorId(id).ifPresent(a -> model.addAttribute("artista", a));
        return "artistas/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarArtista(@ModelAttribute Artista artista) {
        artistaService.guardar(artista);
        return "redirect:/artistas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarArtista(@PathVariable Long id) {
        artistaService.eliminar(id);
        return "redirect:/artistas";
    }
}
*/

//previo ArtistaController.java
/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.ArtistaService;
import com.tectoons.tectoonsweb.service.SeguidorService;
import java.util.Set;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private SeguidorService seguidorService;

    /*@GetMapping
    public String listarArtistas(Model model) {
        List<Artista> artistas = artistaService.listarTodos();
        model.addAttribute("artistas", artistas);
        return "artistas/listar";
    }*/

// ✅ Vista Thymeleaf
/*@GetMapping
public String listarArtistas(Model model) {
    List<Artista> artistas = artistaService.listarTodos();
    model.addAttribute("tituloPagina", "Artistas | TecToons");
    model.addAttribute("artistas", artistas);
    return "artistas"; // /templates/artistas.html
}*/
/* 
    @GetMapping
public String listarArtistas(Model model, @SessionAttribute(name = "usuarioActual", required = false) Usuario usuario) {
    List<Artista> artistas = artistaService.listarTodos();
    model.addAttribute("artistas", artistas);
    model.addAttribute("usuarioId", usuario != null ? usuario.getId() : null);

    if (usuario != null) {
        Set<Long> seguidoIds = seguidorService.obtenerIdsSeguidosPorUsuario(usuario.getId());
        model.addAttribute("seguidoIds", seguidoIds);
    } else {
        model.addAttribute("seguidoIds", Set.of());
    }
    return "artistas";
}

@PostMapping("/seguir/{id}")
public String seguirArtista(@PathVariable Long id, HttpSession session, RedirectAttributes redirect) {
    Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
    if (usuario == null) {
        redirect.addFlashAttribute("mensaje", "Debes iniciar sesión para seguir artistas.");
        return "redirect:/login";
    }
    seguidorService.toggleFollow(usuario.getId(), id);
    return "redirect:/artistas";
}


    // ✅ API REST opcional (para JS dinámico)
    @GetMapping("/api/artistas")
    @ResponseBody
    public List<Artista> listarArtistasApi() {
        return artistaService.listarTodos();
    }

    /*@GetMapping("/detalle/{id}")
    public String detalleArtista(@PathVariable Long id, Model model) {
        artistaService.obtenerPorId(id).ifPresentOrElse(
            a -> model.addAttribute("artista", a),
            () -> model.addAttribute("error", "Artista no encontrado.")
        );
        return "artistas/detalle-artista";
    }*/
/*
    // ✅ Vista detalle (si haces click en un artista)
    @GetMapping("/{id}")
    public String verArtista(@PathVariable Long id, Model model) {
        Optional<Artista> artista = artistaService.obtenerPorId(id);
        if (artista == null) {
            return "redirect:/artistas";
        }
        model.addAttribute("artista", artista);
        return "detalle-artista";
    }

    @GetMapping("/nuevo")
    public String nuevoArtista(Model model) {
        model.addAttribute("artista", new Artista());
        return "artistas/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarArtista(@Valid @ModelAttribute("artista") Artista artista,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Corrige los errores del formulario.");
            return "artistas/nuevo";
        }
        artistaService.guardar(artista);
        return "redirect:/artistas";
    }

    @GetMapping("/editar/{id}")
    public String editarArtista(@PathVariable Long id, Model model) {
        artistaService.obtenerPorId(id).ifPresent(a -> model.addAttribute("artista", a));
        return "artistas/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarArtista(@Valid @ModelAttribute("artista") Artista artista,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Corrige los errores del formulario.");
            return "artistas/editar";
        }
        artistaService.guardar(artista);
        return "redirect:/artistas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarArtista(@PathVariable Long id) {
        artistaService.eliminar(id);
        return "redirect:/artistas";
    }
}*/

/*
package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.ArtistaService;
import com.tectoons.tectoonsweb.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/artista")
@Controller
public class ArtistaController {
    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private UsuarioService usuarioService;

    // ================================================
    // GET - Mostrar perfil del artista
    // ================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @GetMapping("/perfil")
    public String mostrarPerfil(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        Usuario usuario = usuarioService.buscarPorId(usuarioDetails.getId()).orElseThrow();
        Artista artista = artistaService.obtenerPorUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("artista", artista);
        return "artista/perfil-artista";
    }

    // ================================================
    // GET - Mostrar formulario de edición del perfil
    // ================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @GetMapping("/editar-perfil")
    public String editarPerfilForm(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        Usuario usuario = usuarioService.buscarPorId(usuarioDetails.getId()).orElseThrow();
        Artista artista = artistaService.obtenerPorUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("artista", artista);
        return "artista/editar-perfil-artista";
    }

    // ================================================
    // POST - Guardar cambios en el perfil
    // ================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @PostMapping("/editar-perfil")
    public String guardarEdicion(
            @Valid @ModelAttribute("usuario") Usuario usuarioForm,
            @Valid @ModelAttribute("artista") Artista artistaForm,
            @AuthenticationPrincipal CustomUserDetails usuarioDetails,
            Model model) {

        try {
            // Actualizar datos del usuario
            Usuario usuarioExistente = usuarioService.buscarPorId(usuarioDetails.getId()).orElseThrow();
            usuarioExistente.setNombreCompleto(usuarioForm.getNombreCompleto());
            usuarioExistente.setNombreUsuario(usuarioForm.getNombreUsuario());
            usuarioExistente.setCorreo(usuarioForm.getCorreo());
            usuarioExistente.setFechaNacimiento(usuarioForm.getFechaNacimiento());
            usuarioExistente.setUbicacion(usuarioForm.getUbicacion());
            usuarioExistente.setCelular(usuarioForm.getCelular());
            usuarioExistente.setDescripcion(usuarioForm.getDescripcion());
            usuarioExistente.setBannerPerfil(usuarioForm.getBannerPerfil());
            usuarioExistente.setFotoPerfil(usuarioForm.getFotoPerfil());

            usuarioService.guardar(usuarioExistente);

            // Actualizar datos del artista
            Artista artistaExistente = artistaService.obtenerPorUsuarioId(usuarioExistente.getId()).orElseThrow();
            artistaExistente.setNombreArtistico(artistaForm.getNombreArtistico());
            artistaExistente.setCategoriaPrincipal(artistaForm.getCategoriaPrincipal());
            artistaExistente.setBiografiaLarga(artistaForm.getBiografiaLarga());
            artistaExistente.setInstagram(artistaForm.getInstagram());
            artistaExistente.setTiktok(artistaForm.getTiktok());
            artistaExistente.setX(artistaForm.getX());
            artistaExistente.setYoutube(artistaForm.getYoutube());
            artistaExistente.setPortafolioUrl(artistaForm.getPortafolioUrl());
            artistaExistente.setSitioWeb(artistaForm.getSitioWeb());

            artistaService.guardar(artistaExistente);

            model.addAttribute("exito", "Perfil actualizado correctamente");
            return "redirect:/artista/perfil";
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
            return "artista/editar-perfil-artista";
        }
    }
}
*/

/* PREVIO
package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.ArtistaService;
import com.tectoons.tectoonsweb.service.SeguidorService;
import com.tectoons.tectoonsweb.service.UsuarioService;

import java.util.HashSet;
import java.util.List;
//import java.util.Optional;
import java.util.Set;
//import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/artista")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SeguidorService seguidorService;

    // ================================
    // LISTAR TODOS LOS ARTISTAS (cards)
    // ================================
    @GetMapping("/lista-artistas")
    public String listarArtistas(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        List<Artista> artistas = artistaService.listarTodos();
        model.addAttribute("artistas", artistas);

        // IDs de artistas que el usuario ya sigue
        Set<Long> idsSeguidos = new HashSet<>();
        if (usuarioDetails != null) {
            idsSeguidos = seguidorService.obtenerIdsSeguidosPorUsuario(usuarioDetails.getId());
        }
        model.addAttribute("idsSeguidos", idsSeguidos);

        return "artista/lista-artistas"; // Thymeleaf
    }

    // ================================
    // PERFIL DETALLE DE UN ARTISTA
    // ================================
    @GetMapping("/detalle")
    public String detalleArtista(@RequestParam Long id,
                                 @AuthenticationPrincipal CustomUserDetails usuarioDetails,
                                 Model model) {
        Artista artista = artistaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        boolean siguiendo = false;
        if (usuarioDetails != null) {
            siguiendo = seguidorService.existeRelacion(usuarioDetails.getId(), id);
        }

        model.addAttribute("artista", artista);
        model.addAttribute("siguiendo", siguiendo);
        model.addAttribute("totalSeguidores", seguidorService.contarSeguidores(id));

        return "artista/detalle-artista";
    }

    // ================================================
    // GET - Mostrar perfil del artista
    // ================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @GetMapping("/artista/perfil")
    public String mostrarPerfil(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        Usuario usuario = usuarioService.buscarPorId(usuarioDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Artista artista = artistaService.obtenerPorUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("artista", artista);
        return "artista/perfil-artista";
    }

    // ================================================
    // GET - Mostrar formulario de edición del perfil
    // ================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @GetMapping("/artista/editar-perfil")
    public String editarPerfilForm(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        Usuario usuario = usuarioService.buscarPorId(usuarioDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Artista artista = artistaService.obtenerPorUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("artista", artista);
        return "artista/editar-perfil-artista";
    }

    // ================================================
    // POST - Guardar cambios en el perfil
    // ================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @PostMapping("/artista/editar-perfil")
    public String guardarEdicion(
            @ModelAttribute("usuario") Usuario usuarioForm,
            BindingResult resultUsuario,
            @ModelAttribute("artista") Artista artistaForm,
            BindingResult resultArtista,
            @AuthenticationPrincipal CustomUserDetails usuarioDetails,
            @RequestParam(name = "repetirContrasena", required = false) String repetirContrasena,
            Model model) {

        // Validación de formulario
        if (resultUsuario.hasErrors() || resultArtista.hasErrors()) {
            model.addAttribute("error", "Por favor corrige los errores del formulario.");
            return "artista/editar-perfil-artista";
        }

        // Validación de contraseña opcional
        if (usuarioForm.getContrasena() != null && !usuarioForm.getContrasena().isBlank()) {
            if (!usuarioForm.getContrasena().equals(repetirContrasena)) {
                model.addAttribute("error", "Las contraseñas no coinciden.");
                return "artista/editar-perfil-artista";
            }
            if (usuarioForm.getContrasena().length() < 8) {
                model.addAttribute("error", "La contraseña debe tener al menos 8 caracteres.");
                return "artista/editar-perfil-artista";
            }
        }

        // Validación de celular opcional
        if (usuarioForm.getCelular() != null && !usuarioForm.getCelular().isBlank()) {
            if (!usuarioForm.getCelular().matches("\\d{9}")) {
                model.addAttribute("error", "El número de celular debe tener 9 dígitos.");
                return "artista/editar-perfil-artista";
            }
        }

        try {
            Long usuarioId = usuarioDetails.getId();

            // Actualizar perfil del usuario
            usuarioService.actualizarPerfil(usuarioId, usuarioForm);

            // Actualizar perfil del artista
            artistaService.actualizarPerfilArtista(usuarioId, artistaForm);

            model.addAttribute("exito", "Perfil actualizado correctamente.");
            return "artista/editar-perfil-artista";

        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
            return "artista/editar-perfil-artista";
        }
    }
}
*/

/*
package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.ArtistaService;
import com.tectoons.tectoonsweb.service.SeguidorService;
import com.tectoons.tectoonsweb.service.UsuarioService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SeguidorService seguidorService;

    // ===========================================================
    // LISTA PÚBLICA DE ARTISTAS
    // ===========================================================
    @GetMapping("/lista-artistas")
    public String listarArtistas(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        List<Artista> artistas = artistaService.listarTodos();
        model.addAttribute("artistas", artistas);

        Set<Long> idsSeguidos = new HashSet<>();
        boolean loggedIn = false;

        if (usuarioDetails != null) {
            idsSeguidos = seguidorService.obtenerIdsSeguidosPorUsuario(usuarioDetails.getId());
            loggedIn = true;
        }

        model.addAttribute("idsSeguidos", idsSeguidos);
        model.addAttribute("loggedIn", loggedIn); // <<== indicador de sesión

        return "artista/lista-artistas"; // Thymeleaf
    }

    // ===========================================================
    // PERFIL DETALLE DE UN ARTISTA (público)
    // ===========================================================
    @GetMapping("/detalle")
    public String detalleArtista(@RequestParam Long id,
                                 @AuthenticationPrincipal CustomUserDetails usuarioDetails,
                                 Model model) {
        Artista artista = artistaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        boolean siguiendo = false;
        if (usuarioDetails != null) {
            siguiendo = seguidorService.existeRelacion(usuarioDetails.getId(), id);
        }

        model.addAttribute("artista", artista);
        model.addAttribute("siguiendo", siguiendo);
        model.addAttribute("totalSeguidores", seguidorService.contarSeguidores(id));

        return "artista/detalle-artista";
    }

    // ===========================================================
    // PERFIL PRIVADO DEL ARTISTA
    // ===========================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @GetMapping("/artista/perfil")
    public String mostrarPerfil(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        Usuario usuario = usuarioService.buscarPorId(usuarioDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Artista artista = artistaService.obtenerPorUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("artista", artista);
        return "artista/perfil-artista";
    }

    // ===========================================================
    // FORMULARIO DE EDICIÓN DEL PERFIL
    // ===========================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @GetMapping("/artista/editar-perfil")
    public String editarPerfilForm(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        Usuario usuario = usuarioService.buscarPorId(usuarioDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Artista artista = artistaService.obtenerPorUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("artista", artista);
        return "artista/editar-perfil-artista";
    }

    // ===========================================================
    // GUARDAR CAMBIOS EN PERFIL
    // ===========================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @PostMapping("/artista/editar-perfil")
    public String guardarEdicion(
            @ModelAttribute("usuario") Usuario usuarioForm,
            BindingResult resultUsuario,
            @ModelAttribute("artista") Artista artistaForm,
            BindingResult resultArtista,
            @AuthenticationPrincipal CustomUserDetails usuarioDetails,
            @RequestParam(name = "repetirContrasena", required = false) String repetirContrasena,
            Model model) {

        if (resultUsuario.hasErrors() || resultArtista.hasErrors()) {
            model.addAttribute("error", "Por favor corrige los errores del formulario.");
            return "artista/editar-perfil-artista";
        }

        if (usuarioForm.getContrasena() != null && !usuarioForm.getContrasena().isBlank()) {
            if (!usuarioForm.getContrasena().equals(repetirContrasena)) {
                model.addAttribute("error", "Las contraseñas no coinciden.");
                return "artista/editar-perfil-artista";
            }
            if (usuarioForm.getContrasena().length() < 8) {
                model.addAttribute("error", "La contraseña debe tener al menos 8 caracteres.");
                return "artista/editar-perfil-artista";
            }
        }

        if (usuarioForm.getCelular() != null && !usuarioForm.getCelular().isBlank()) {
            if (!usuarioForm.getCelular().matches("\\d{9}")) {
                model.addAttribute("error", "El número de celular debe tener 9 dígitos.");
                return "artista/editar-perfil-artista";
            }
        }

        try {
            Long usuarioId = usuarioDetails.getId();

            usuarioService.actualizarPerfil(usuarioId, usuarioForm);
            artistaService.actualizarPerfilArtista(usuarioId, artistaForm);

            model.addAttribute("exito", "Perfil actualizado correctamente.");
            return "artista/editar-perfil-artista";

        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
            return "artista/editar-perfil-artista";
        }
    }

    // ===========================================================
    // MÉTODO AUXILIAR: Obtener usuario logueado (opcional)
    // ===========================================================
    private Usuario getUsuarioLogueado(@AuthenticationPrincipal CustomUserDetails usuarioDetails) {
        if (usuarioDetails != null) {
            return usuarioService.buscarPorId(usuarioDetails.getId()).orElse(null);
        }
        return null;
    }
}
*/

package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.ArtistaService;
import com.tectoons.tectoonsweb.service.SeguidorService;
import com.tectoons.tectoonsweb.service.UsuarioService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SeguidorService seguidorService;

    // ===========================================================
    // LISTA PÚBLICA DE ARTISTAS
    // ===========================================================
    @GetMapping("/lista-artistas")
    public String listarArtistas(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        List<Artista> artistas = artistaService.listarTodos();
        model.addAttribute("artistas", artistas);

        // Obtener usuario logueado
        Usuario usuario = getUsuarioLogueado(usuarioDetails);
        boolean loggedIn = usuario != null;

        Set<Long> idsSeguidos = new HashSet<>();
        if (loggedIn) {
            idsSeguidos = seguidorService.obtenerIdsSeguidosPorUsuario(usuario.getId());
        }

        model.addAttribute("idsSeguidos", idsSeguidos);
        model.addAttribute("loggedIn", loggedIn);

        return "artista/lista-artistas";
    }

    // ===========================================================
    // PERFIL DETALLE DE UN ARTISTA (público)
    // ===========================================================
    @GetMapping("/detalle-artista/{id}")
    public String detalleArtista(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails usuarioDetails,
            Model model) {
        Artista artista = artistaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        Usuario usuario = getUsuarioLogueado(usuarioDetails);
        boolean siguiendo = false;
        if (usuario != null) {
            siguiendo = seguidorService.existeRelacion(usuario.getId(), id);
        }

        model.addAttribute("artista", artista);
        model.addAttribute("siguiendo", siguiendo);
        model.addAttribute("totalSeguidores", seguidorService.contarSeguidores(id));
        model.addAttribute("usuario", usuario);
        long seguidoresCount = seguidorService.contarSeguidores(artista.getId());
        model.addAttribute("seguidoresCount", seguidoresCount);

        return "artista/detalle-artista";
    }

    // ===========================================================
    // PERFIL PRIVADO DEL ARTISTA
    // ===========================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @GetMapping("/artista/mi-perfil")
    public String mostrarPerfil(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        Usuario usuario = getUsuarioLogueado(usuarioDetails);
        if (usuario == null)
            throw new RuntimeException("Usuario no encontrado");

        Artista artista = artistaService.obtenerPorUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        long seguidoresCount = seguidorService.contarSeguidores(artista.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("artista", artista);
        model.addAttribute("seguidoresCount", seguidoresCount); // <- aquí agregamos
        return "artista/perfil-artista";
    }

    // ===========================================================
    // FORMULARIO DE EDICIÓN DEL PERFIL
    // ===========================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @GetMapping("/artista/editar-perfil")
    public String editarPerfilForm(@AuthenticationPrincipal CustomUserDetails usuarioDetails, Model model) {
        Usuario usuario = getUsuarioLogueado(usuarioDetails);
        if (usuario == null)
            throw new RuntimeException("Usuario no encontrado");

        Artista artista = artistaService.obtenerPorUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("artista", artista);
        return "artista/editar-perfil-artista";
    }

    // ===========================================================
    // GUARDAR CAMBIOS EN PERFIL
    // ===========================================================
    @PreAuthorize("hasRole('ARTISTA')")
    @PostMapping("/artista/editar-perfil")
    public String guardarEdicion(
            @ModelAttribute("usuario") Usuario usuarioForm,
            BindingResult resultUsuario,
            @ModelAttribute("artista") Artista artistaForm,
            BindingResult resultArtista,
            @AuthenticationPrincipal CustomUserDetails usuarioDetails,
            @RequestParam(name = "repetirContrasena", required = false) String repetirContrasena,
            Model model) {

        if (resultUsuario.hasErrors() || resultArtista.hasErrors()) {
            model.addAttribute("error", "Por favor corrige los errores del formulario.");
            return "artista/editar-perfil-artista";
        }

        if (usuarioForm.getContrasena() != null && !usuarioForm.getContrasena().isBlank()) {
            if (!usuarioForm.getContrasena().equals(repetirContrasena)) {
                model.addAttribute("error", "Las contraseñas no coinciden.");
                return "artista/editar-perfil-artista";
            }
            if (usuarioForm.getContrasena().length() < 8) {
                model.addAttribute("error", "La contraseña debe tener al menos 8 caracteres.");
                return "artista/editar-perfil-artista";
            }
        }

        if (usuarioForm.getCelular() != null && !usuarioForm.getCelular().isBlank()) {
            if (!usuarioForm.getCelular().matches("\\d{9}")) {
                model.addAttribute("error", "El número de celular debe tener 9 dígitos.");
                return "artista/editar-perfil-artista";
            }
        }

        try {
            Usuario usuario = getUsuarioLogueado(usuarioDetails);
            if (usuario == null)
                throw new RuntimeException("Usuario no encontrado");

            usuarioService.actualizarPerfil(usuario.getId(), usuarioForm);
            artistaService.actualizarPerfilArtista(usuario.getId(), artistaForm);

            model.addAttribute("exito", "Perfil actualizado correctamente.");
            return "artista/editar-perfil-artista";

        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
            return "artista/editar-perfil-artista";
        }
    }

    // ===========================================================
    // MÉTODO AUXILIAR: obtener usuario logueado
    // ===========================================================
    private Usuario getUsuarioLogueado(CustomUserDetails usuarioDetails) {
        if (usuarioDetails != null) {
            return usuarioService.buscarPorId(usuarioDetails.getId()).orElse(null);
        }
        return null;
    }
}
