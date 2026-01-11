/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/obras")
@CrossOrigin(origins = "*")
public class ObraRestController {

    @Autowired
    private ObraService obraService;

    @GetMapping
    public List<Obra> listarTodos() {
        return obraService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obra> obtenerPorId(@PathVariable Long id) {
        Optional<Obra> obra = obraService.obtenerPorId(id);
        return obra.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Obra> crear(@RequestBody Obra obra) {
        return ResponseEntity.ok(obraService.guardar(obra));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Obra> actualizar(@PathVariable Long id, @RequestBody Obra obraActualizada) {
        Optional<Obra> obraExistente = obraService.obtenerPorId(id);
        if (obraExistente.isPresent()) {
            Obra obra = obraExistente.get();
            obra.setTitulo(obraActualizada.getTitulo());
            obra.setDescripcion(obraActualizada.getDescripcion());
            obra.setImagenUrl(obraActualizada.getImagenUrl());
            obra.setVideoUrl(obraActualizada.getVideoUrl());
            return ResponseEntity.ok(obraService.guardar(obra));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Obra> obra = obraService.obtenerPorId(id);
        if (obra.isPresent()) {
            obraService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/artista/{artistaId}")
    public List<Obra> buscarPorArtista(@PathVariable Long artistaId) {
        return obraService.buscarPorArtista(artistaId);
    }
}*/

package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/obras")
public class ObraRestController {

    @Autowired
    private ObraService obraService;

    private Usuario getUsuarioLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal()))
            return null;
        return ((CustomUserDetails) auth.getPrincipal()).getUsuario();
    }

    private Artista getArtistaLogueado() {
        Usuario u = getUsuarioLogueado();
        return u != null ? u.getArtista() : null;
    }

    @GetMapping("/{id}")
    public Obra obtenerObra(@PathVariable Long id) {
        Artista artista = getArtistaLogueado();
        Obra obra = obraService.obtenerPorId(id).orElseThrow();
        if (!obra.getArtista().getId().equals(artista.getId()))
            throw new RuntimeException("Obra no pertenece al artista");
        return obra;
    }

    @PostMapping
    public Obra crearObra(@RequestBody Obra obra) {
        Artista artista = getArtistaLogueado();
        obra.setArtista(artista);
        if (obra.getVideoUrl() != null && obra.getVideoUrl().isBlank()) {
            obra.setVideoUrl(null);
        }
        obraService.guardar(obra);
        return obra;
    }

    @PutMapping("/{id}")
    public Obra actualizarObra(@PathVariable Long id, @RequestBody Obra obra) {
        Artista artista = getArtistaLogueado();
        Obra original = obraService.obtenerPorId(id).orElseThrow();
        if (!original.getArtista().getId().equals(artista.getId()))
            throw new RuntimeException("Obra no pertenece al artista");
        obra.setId(id);
        obra.setArtista(artista);
        if (obra.getVideoUrl() != null && obra.getVideoUrl().isBlank()) {
            obra.setVideoUrl(null);
        }
        obraService.guardar(obra);
        return obra;
    }

    @DeleteMapping("/{id}")
    public void eliminarObra(@PathVariable Long id) {
        Artista artista = getArtistaLogueado();
        Obra obra = obraService.obtenerPorId(id).orElseThrow();
        if (!obra.getArtista().getId().equals(artista.getId()))
            throw new RuntimeException("Obra no pertenece al artista");

        obraService.eliminar(id);
    }

    @GetMapping("/artista/mis-obras")
    public List<Obra> listarObrasDelArtista() {
        Artista artista = getArtistaLogueado();
        if (artista == null)
            throw new RuntimeException("No autenticado");

        return obraService.buscarPorArtista(artista.getId());
    }

}
