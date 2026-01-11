/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.service.SeguidorService;
import com.tectoons.tectoonsweb.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/seguidores")
public class SeguidorRestController {

    @Autowired
    private SeguidorService seguidorService;

    // toggle follow (POST)
    @PostMapping("/toggle")
    public Map<String, Object> toggleFollow(@RequestParam Long artistaId, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
        if (usuario == null) {
            return Map.of("ok", false, "error", "no-auth");
        }
        boolean nowFollowing = seguidorService.toggleFollow(usuario.getId(), artistaId);
        long count = seguidorService.contarSeguidores(artistaId);
        return Map.of("ok", true, "following", nowFollowing, "followers", count);
    }

    // comprobar si el usuario ya sigue (GET)
    @GetMapping("/exists")
    public Map<String, Object> exists(@RequestParam Long artistaId, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
        if (usuario == null) return Map.of("ok", true, "exists", false);
        boolean exists = seguidorService.existeRelacion(usuario.getId(), artistaId);
        return Map.of("ok", true, "exists", exists);
    }

    // contar seguidores (GET)
    @GetMapping("/count")
    public Map<String, Object> count(@RequestParam Long artistaId) {
        long count = seguidorService.contarSeguidores(artistaId);
        return Map.of("ok", true, "followers", count);
    }
}
*/

package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.SeguidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/seguidores")
public class SeguidorRestController {

    @Autowired
    private SeguidorService seguidorService;

    // toggle follow
    @PostMapping("/toggle/{artistaId}")
    public Map<String, Object> toggleFollow(@AuthenticationPrincipal CustomUserDetails usuarioDetails,
                                            @PathVariable Long artistaId) {
        if (usuarioDetails == null) {
            return Map.of("ok", false, "error", "no-auth");
        }

        boolean ahoraSigue = seguidorService.toggleFollow(usuarioDetails.getId(), artistaId);
        long totalSeguidores = seguidorService.contarSeguidores(artistaId);

        return Map.of("ok", true, "siguiendo", ahoraSigue, "totalSeguidores", totalSeguidores);
    }

    // verificar si ya sigue
    @GetMapping("/exists/{artistaId}")
    public Map<String, Object> exists(@AuthenticationPrincipal CustomUserDetails usuarioDetails,
                                      @PathVariable Long artistaId) {
        if (usuarioDetails == null) return Map.of("ok", true, "exists", false);
        boolean exists = seguidorService.existeRelacion(usuarioDetails.getId(), artistaId);
        return Map.of("ok", true, "exists", exists);
    }

    // contar seguidores
    @GetMapping("/count/{artistaId}")
    public Map<String, Object> count(@PathVariable Long artistaId) {
        long count = seguidorService.contarSeguidores(artistaId);
        return Map.of("ok", true, "totalSeguidores", count);
    }
}
