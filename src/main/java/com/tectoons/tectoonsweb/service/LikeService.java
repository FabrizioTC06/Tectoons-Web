/*package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.tectoons.tectoonsweb.model.Like;
import com.tectoons.tectoonsweb.repository.LikeRepository;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public List<Like> listarTodos() {
        return likeRepository.findAll();
    }

    public Optional<Like> obtenerPorId(Integer id) {
        return likeRepository.findById(id);
    }

    public Like guardar(Like like) {
        return likeRepository.save(like);
    }

    public void eliminar(Integer id) {
        likeRepository.deleteById(id);
    }

    public boolean existeLike(Integer usuarioId, Integer obraId) {
        return likeRepository.existsByUsuarioIdAndObraId(usuarioId, obraId);
    }

    public int contarLikesPorObra(Integer obraId) {
        return likeRepository.countByObraId(obraId);
    }
}
*/
/*package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.tectoons.tectoonsweb.model.Like;
import com.tectoons.tectoonsweb.repository.LikeRepository;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.ObraRepository;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean toggleLike(Integer obraId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Obra obra = obraRepository.findById(obraId)
                .orElseThrow(() -> new RuntimeException("Obra no encontrada"));

        Optional<Like> existing = likeRepository.findByUsuarioAndObra(usuario, obra);
        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            return false; // se quitó el like
        } else {
            Like like = new Like();
            like.setUsuario(usuario);
            like.setObra(obra);
            likeRepository.save(like);
            return true; // se añadió el like
        }
    }

    public List<Long> obtenerObrasLikedPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return likeRepository.findAllByUsuario(usuario)
                .stream()
                .map(like -> like.getObra().getId())
                .collect(Collectors.toList());
    }
}
*/
/*package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.Optional;
import com.tectoons.tectoonsweb.model.Like;
import com.tectoons.tectoonsweb.repository.LikeRepository;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.ObraRepository;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean toggleLike(Long obraId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Obra obra = obraRepository.findById(obraId)
                .orElseThrow(() -> new RuntimeException("Obra no encontrada"));

        Optional<Like> existing = likeRepository.findByUsuarioAndObra(usuario, obra);
        if (existing.isPresent()) {
            // Quitar like
            likeRepository.delete(existing.get());

            // Disminuir contador en obra
            obra.setLikes(obra.getLikes() - 1);
            obraRepository.save(obra);

            return false;
        } else {
            // Añadir like
            Like like = new Like();
            like.setUsuario(usuario);
            like.setObra(obra);
            likeRepository.save(like);

            // Incrementar contador en obra
            obra.setLikes(obra.getLikes() + 1);
            obraRepository.save(obra);

            return true;
        }
    }

    public Set<Long> obtenerIdsObrasConLike(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return likeRepository.findAllByUsuario(usuario)
                .stream()
                .map(like -> like.getObra().getId())
                .collect(Collectors.toSet());
    }

    public boolean existsLike(Long obraId, Long usuarioId) {
    // ejemplo con JPA
    return likeRepository.existsByObraIdAndUsuarioId(obraId, usuarioId);
}

}
*/

package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Like;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.LikeRepository;
import com.tectoons.tectoonsweb.repository.ObraRepository;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObraRepository obraRepository;

    /**
     * Verifica si un usuario ya dio like a una obra
     */
    public boolean existsLike(Long obraId, Long usuarioId) {
        return likeRepository.existsByObraIdAndUsuarioId(obraId, usuarioId);
    }

    /**
     * Registrar un like
     */
    public boolean addLike(Long obraId, Long usuarioId) {

        if (existsLike(obraId, usuarioId)) {
            return false; // Ya existe
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Obra obra = obraRepository.findById(obraId)
                .orElseThrow(() -> new RuntimeException("Obra no encontrada"));

        Like like = new Like();
        like.setUsuario(usuario);
        like.setObra(obra);

        likeRepository.save(like);

        return true;
    }

    /**
     * Quitar like
     */
    public boolean removeLike(Long obraId, Long usuarioId) {

        Optional<Like> likeOpt = likeRepository.findByObraIdAndUsuarioId(obraId, usuarioId);

        if (likeOpt.isEmpty()) {
            return false;
        }

        likeRepository.delete(likeOpt.get());
        return true;
    }

    /**
     * Alternar like (LIKE ó UNLIKE)
     * Devuelve true si ahora quedó con like
     * Devuelve false si se quitó el like
     */
    public boolean toggleLike(Long obraId, Long usuarioId) {

        if (existsLike(obraId, usuarioId)) {
            removeLike(obraId, usuarioId);
            return false; // ahora quedó SIN like
        } else {
            addLike(obraId, usuarioId);
            return true; // ahora quedó CON like
        }
    }

    /**
     * Contar likes de una obra
     */
    public long countLikes(Long obraId) {
        return likeRepository.countByObraId(obraId);
    }

    /**
     * Obtener lista de IDs de obras que el usuario ha dado like
     */
    public Set<Long> obtenerIdsObrasConLike(Long usuarioId) {
        return likeRepository.findByUsuarioId(usuarioId).stream()
                .map(like -> like.getObra().getId())
                .collect(Collectors.toSet());
    }
}

