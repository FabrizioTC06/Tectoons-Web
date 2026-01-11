/*package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.tectoons.tectoonsweb.model.Seguidor;
import com.tectoons.tectoonsweb.repository.SeguidorRepository;

@Service
public class SeguidorService {

    @Autowired
    private SeguidorRepository seguidorRepository;

    public List<Seguidor> listarTodos() {
        return seguidorRepository.findAll();
    }

    public Optional<Seguidor> obtenerPorId(Long id) {
        return seguidorRepository.findById(id);
    }

    public Seguidor guardar(Seguidor seguidor) {
        return seguidorRepository.save(seguidor);
    }

    public void eliminar(Long id) {
        seguidorRepository.deleteById(id);
    }

    public boolean existeRelacion(Integer seguidorId, Integer seguidoId) {
        return seguidorRepository.existsBySeguidorIdAndSeguidoId(seguidorId, seguidoId);
    }

    public List<Seguidor> buscarSeguidoresDeArtista(Integer seguidoId) {
        return seguidorRepository.findBySeguidoId(seguidoId);
    }

    public List<Seguidor> buscarSeguidosPorUsuario(Integer seguidorId) {
        return seguidorRepository.findBySeguidorId(seguidorId);
    }
}*/


package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Seguidor;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.repository.ArtistaRepository;
import com.tectoons.tectoonsweb.repository.SeguidorRepository;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;
import java.time.LocalDateTime;

@Service
public class SeguidorService {

    @Autowired
    private SeguidorRepository seguidorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    // Listar todos los seguidores
    public List<Seguidor> listarTodos() {
        return seguidorRepository.findAll();
    }

    public Optional<Seguidor> obtenerPorId(Long id) {
        return seguidorRepository.findById(id);
    }

    @Transactional
    public Seguidor guardar(Seguidor seguidor) {
        return seguidorRepository.save(seguidor);
    }

    @Transactional
    public void eliminar(Long id) {
        seguidorRepository.deleteById(id);
    }

    // Verificar si existe relación
    public boolean existeRelacion(Long seguidorId, Long seguidoId) {
        return seguidorRepository.existsBySeguidor_IdAndSeguido_Id(seguidorId, seguidoId);
    }

    // Contar seguidores de un artista
    public long contarSeguidores(Long seguidoId) {
        return seguidorRepository.countBySeguido_Id(seguidoId);
    }

    public List<Seguidor> listarSeguidoresDeArtista(Long seguidoId) {
        return seguidorRepository.findBySeguido_Id(seguidoId);
    }

    public List<Seguidor> listarSeguidosDeUsuario(Long seguidorId) {
        return seguidorRepository.findBySeguidor_Id(seguidorId);
    }

    public Set<Long> obtenerIdsSeguidosPorUsuario(Long seguidorId) {
        return seguidorRepository.findBySeguidor_Id(seguidorId)
                .stream()
                .map(s -> s.getSeguido().getId())
                .collect(Collectors.toSet());
    }

    // Método toggle follow
    @Transactional
    public boolean toggleFollow(Long seguidorId, Long seguidoId) {
        if (seguidorId == null || seguidoId == null) return false;

        Optional<Seguidor> existente = seguidorRepository.findBySeguidor_IdAndSeguido_Id(seguidorId, seguidoId);
        if (existente.isPresent()) {
            // deja de seguir
            seguidorRepository.delete(existente.get());
            return false;
        } else {
            // empieza a seguir
            Usuario usuario = usuarioRepository.findById(seguidorId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no existe"));
            Artista artista = artistaRepository.findById(seguidoId)
                    .orElseThrow(() -> new IllegalArgumentException("Artista no existe"));

            Seguidor nuevo = new Seguidor();
            nuevo.setSeguidor(usuario);
            nuevo.setSeguido(artista);
            nuevo.setFecha(LocalDateTime.now());

            seguidorRepository.save(nuevo);
            return true;
        }
    }
}


/*@Service
public class SeguidorService {

    @Autowired
    private SeguidorRepository seguidorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    public SeguidorService(SeguidorRepository seguidorRepository) {
        this.seguidorRepository = seguidorRepository;
    }

    public List<Seguidor> listarTodos() {
        return seguidorRepository.findAll();
    }

    public Optional<Seguidor> obtenerPorId(Long id) {
        return seguidorRepository.findById(id);
    }

    @Transactional
    public Seguidor guardar(Seguidor seguidor) {
        return seguidorRepository.save(seguidor);
    }

    @Transactional
    public void eliminar(Long id) {
        seguidorRepository.deleteById(id);
    }

    public boolean existeRelacion(Long seguidorId, Long seguidoId) {
        return seguidorRepository.existsBySeguidor_IdAndSeguido_Id(seguidorId, seguidoId);
    }

    public long contarSeguidores(Long seguidoId) {
        return seguidorRepository.countBySeguido_Id(seguidoId);
    }

    public List<Seguidor> listarSeguidoresDeArtista(Long seguidoId) {
        return seguidorRepository.findBySeguido_Id(seguidoId);
    }

    public List<Seguidor> listarSeguidosDeUsuario(Long seguidorId) {
        return seguidorRepository.findBySeguidor_Id(seguidorId);
    }

    public Set<Long> obtenerIdsSeguidosPorUsuario(Long seguidorId) {
        return seguidorRepository.findBySeguidor_Id(seguidorId)
                .stream()
                .map(s -> s.getSeguido().getId())
                .collect(Collectors.toSet());
    }

    @Transactional
    public boolean toggleFollow(Long seguidorId, Long seguidoId) {
        if (seguidorId == null || seguidoId == null) return false;
        if (seguidorRepository.existsBySeguidor_IdAndSeguido_Id(seguidorId, seguidoId)) {
            // quitar follow
            seguidorRepository.deleteBySeguidor_IdAndSeguido_Id(seguidorId, seguidoId);
            return false; // ahora NO sigue
        } else {
            // crear follow
            Optional<Usuario> uOpt = usuarioRepository.findById(seguidorId);
            Optional<Artista> aOpt = artistaRepository.findById(seguidoId);
            if (uOpt.isPresent() && aOpt.isPresent()) {
                Seguidor s = new Seguidor();
                s.setSeguidor(uOpt.get());
                s.setSeguido(aOpt.get());
                s.setFecha(LocalDateTime.now());
                seguidorRepository.save(s);
                return true; // ahora SI sigue
            } else {
                throw new IllegalArgumentException("Usuario o Artista no existe");
            }
        }
    }*/

    /*public boolean existeRelacion(Long seguidorId, Long seguidoId) {
        return seguidorRepository.existsBySeguidor_IdAndSeguido_Id(seguidorId, seguidoId);
    }

    public List<Seguidor> buscarSeguidoresDeArtista(Long seguidoId) {
        return seguidorRepository.findBySeguido_Id(seguidoId);
    }

    public List<Seguidor> buscarSeguidosPorUsuario(Long seguidorId) {
        return seguidorRepository.findBySeguidor_Id(seguidorId);
    }

    @Transactional
    public boolean toggleSeguimiento(Long seguidorId, Long seguidoId) {
        Optional<Seguidor> existente = seguidorRepository.findBySeguidor_IdAndSeguido_Id(seguidorId, seguidoId);
        if (existente.isPresent()) {
            seguidorRepository.delete(existente.get());
            return false; // dejó de seguir
        } else {
            Seguidor nuevo = new Seguidor();
            Usuario usuario = new Usuario();
            usuario.setId(seguidorId);
            Artista artista = new Artista();
            artista.setId(seguidoId);
            nuevo.setSeguidor(usuario);
            nuevo.setSeguido(artista);
            seguidorRepository.save(nuevo);
            return true; // empezó a seguir
        }
    }*/
//}

