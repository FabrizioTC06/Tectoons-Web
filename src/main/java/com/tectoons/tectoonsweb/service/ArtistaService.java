/*package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.repository.ArtistaRepository;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    public List<Artista> listarTodos() {
        return artistaRepository.findAll();
    }

    public Optional<Artista> obtenerPorId(Integer id) {
        return artistaRepository.findById(id);
    }

    public Artista guardar(Artista artista) {
        return artistaRepository.save(artista);
    }

    public void eliminar(Integer id) {
        artistaRepository.deleteById(id);
    }

    public List<Artista> buscarPorEspecialidad(String especialidad) {
        return artistaRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    }

    public List<Artista> buscarPorNombreArtistico(String nombre) {
        return artistaRepository.findByNombreArtisticoContainingIgnoreCase(nombre);
    }
}
*/

/*package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.repository.ArtistaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    public List<Artista> listarTodos() {
        return artistaRepository.findAll();
    }

    public Optional<Artista> obtenerPorId(Long id) {
        return artistaRepository.findById(id);
    }

    @Transactional
    public Artista guardar(Artista artista) {
        return artistaRepository.save(artista);
    }

    @Transactional
    public void eliminar(Long id) {
        artistaRepository.deleteById(id);
    }

    public List<Artista> buscarPorEspecialidad(String especialidad) {
        return artistaRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    }

    public List<Artista> buscarPorNombreArtistico(String nombre) {
        return artistaRepository.findByNombreArtisticoContainingIgnoreCase(nombre);
    }

    public Optional<Artista> obtenerPorUsuarioId(Long usuarioId) {
        return artistaRepository.findByUsuarioId(usuarioId);
    }
}*/

package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.repository.ArtistaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    // Listar todos los artistas
    public List<Artista> listarTodos() {
        return artistaRepository.findAll();
    }

    // Obtener artista por ID
    public Optional<Artista> obtenerPorId(Long id) {
        return artistaRepository.findById(id);
    }

    // Guardar o actualizar artista
    @Transactional
    public Artista guardar(Artista artista) {
        return artistaRepository.save(artista);
    }

    // Eliminar artista por ID
    @Transactional
    public void eliminar(Long id) {
        artistaRepository.deleteById(id);
    }

    // Buscar artistas por nombre artístico (búsqueda parcial)
    public List<Artista> buscarPorNombreArtistico(String nombre) {
        return artistaRepository.findByNombreArtisticoContainingIgnoreCase(nombre);
    }

    // Obtener artista por ID de usuario (relación 1 a 1)
    public Optional<Artista> obtenerPorUsuarioId(Long usuarioId) {
        return artistaRepository.findByUsuarioId(usuarioId);
    }

    // Método comentado para futura búsqueda por especialidad
    // public List<Artista> buscarPorEspecialidad(String especialidad) {
    // return
    // artistaRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    // }

    // ===============================================
    // ACTUALIZAR PERFIL DEL ARTISTA
    // ===============================================
    @Transactional
    public void actualizarPerfilArtista(Long usuarioId, Artista datos) {
        Artista artista = artistaRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        // Campos obligatorios
        artista.setNombreArtistico(datos.getNombreArtistico());
        artista.setCategoriaPrincipal(datos.getCategoriaPrincipal());

        // Campos opcionales
        artista.setBiografiaLarga(
                (datos.getBiografiaLarga() != null && !datos.getBiografiaLarga().isBlank())
                        ? datos.getBiografiaLarga()
                        : artista.getBiografiaLarga());

        artista.setInstagram((datos.getInstagram() != null && !datos.getInstagram().isBlank())
                ? datos.getInstagram()
                : artista.getInstagram());

        artista.setTiktok((datos.getTiktok() != null && !datos.getTiktok().isBlank())
                ? datos.getTiktok()
                : artista.getTiktok());

        artista.setX((datos.getX() != null && !datos.getX().isBlank())
                ? datos.getX()
                : artista.getX());

        artista.setYoutube((datos.getYoutube() != null && !datos.getYoutube().isBlank())
                ? datos.getYoutube()
                : artista.getYoutube());

        artista.setPortafolioUrl((datos.getPortafolioUrl() != null && !datos.getPortafolioUrl().isBlank())
                ? datos.getPortafolioUrl()
                : artista.getPortafolioUrl());

        artista.setSitioWeb((datos.getSitioWeb() != null && !datos.getSitioWeb().isBlank())
                ? datos.getSitioWeb()
                : artista.getSitioWeb());

        artistaRepository.save(artista);
    }
}
