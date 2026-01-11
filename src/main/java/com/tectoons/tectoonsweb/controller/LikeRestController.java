/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Like;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.LikeRepository;
import com.tectoons.tectoonsweb.repository.ObraRepository;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikeRestController {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/{obraId}")
    public ResponseEntity<?> darLike(@PathVariable Long obraId, HttpSession session) {

        // Verificar usuario logueado
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return ResponseEntity.status(401).body("Necesitas iniciar sesión primero");
        }

        Optional<Obra> obraOpt = obraRepository.findById(obraId);
        if (obraOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Obra no encontrada");
        }

        // Verificar si ya dio like
        if (likeRepository.existsByUsuarioIdAndObraId(usuario.getId().intValue(), obraId.intValue())) {
            return ResponseEntity.badRequest().body("Ya has dado like a esta obra");
        }

        // Guardar like
        Like like = new Like();
        like.setObra(obraOpt.get());
        like.setUsuario(usuario);
        likeRepository.save(like);

        // Actualizar contador de likes en la obra
        int totalLikes = likeRepository.countByObraId(obraId.intValue());
        obraOpt.get().setLikes(totalLikes);
        obraRepository.save(obraOpt.get());

        return ResponseEntity.ok(totalLikes);
    }
}
*/

/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeRestController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Object>> toggleLike(
            @RequestParam Long obraId,
            @RequestParam Long usuarioId) {

        boolean liked = likeService.toggleLike(obraId, usuarioId);
        System.out.println("Click detectado en obra con id: " + obraId);

        // Retornamos el estado y el mensaje
        return ResponseEntity.ok(Map.of(
                "liked", liked,
                "message", liked ? "Te gusta" : "Se quitó tu like"
        ));
    }

    @GetMapping("/exists")
public ResponseEntity<Map<String, Object>> existsLike(
        @RequestParam Long obraId,
        @RequestParam Long usuarioId) {

    boolean exists = likeService.existsLike(obraId, usuarioId); // método que devuelve true/false
    return ResponseEntity.ok(Map.of("exists", exists));
}

}
*/
package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeRestController {

    @Autowired
    private LikeService likeService;

    // Toggle like usando el usuario autenticado
    @PostMapping("/{obraId}")
    public ResponseEntity<?> toggleLike(
            @PathVariable Long obraId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        if (user == null) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("error", "Debes iniciar sesión para dar like.");
            resp.put("logged", false);
            return ResponseEntity.status(401).body(resp);
        }

        Long usuarioId = user.getUsuario().getId();

        boolean newStatus = likeService.toggleLike(obraId, usuarioId);
        long totalLikes = likeService.countLikes(obraId);

        Map<String, Object> resp = new HashMap<>();
        resp.put("liked", newStatus);
        resp.put("totalLikes", totalLikes);
        resp.put("logged", true);

        return ResponseEntity.ok(resp);
    }

    // Comprueba si el usuario autenticado ya dio like a la obra
    @GetMapping("/exists/{obraId}")
    public ResponseEntity<?> existsLike(
            @PathVariable Long obraId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        if (user == null) {
            return ResponseEntity.ok(Map.of("exists", false, "logged", false));
        }
        Long usuarioId = user.getUsuario().getId();
        boolean exists = likeService.existsLike(obraId, usuarioId);
        return ResponseEntity.ok(Map.of("exists", exists, "logged", true));
    }

    // (opcional) obtener contador sin autenticación
    @GetMapping("/count/{obraId}")
    public ResponseEntity<?> countLikes(@PathVariable Long obraId) {
        long total = likeService.countLikes(obraId);
        return ResponseEntity.ok(Map.of("totalLikes", total));
    }
}
