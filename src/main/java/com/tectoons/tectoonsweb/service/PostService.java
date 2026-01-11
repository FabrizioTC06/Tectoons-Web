package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Post;
import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.repository.PostRepository;
import com.tectoons.tectoonsweb.repository.ArtistaRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ArtistaRepository artistaRepository;

    public PostService(PostRepository postRepository, ArtistaRepository artistaRepository) {
        this.postRepository = postRepository;
        this.artistaRepository = artistaRepository;
    }

    // ===========================
    // CREAR POST (NUEVO DIBUJO)
    // ===========================
    @Transactional
    public Post crearPost(Long artistaId, Post post) {

        Artista artista = artistaRepository.findById(artistaId)
                .orElseThrow(() -> new RuntimeException("Artista no encontrado"));

        post.setArtista(artista); 
        return postRepository.save(post);
    }

    // ===========================
    // LISTAR POSTS
    // ===========================
    public List<Post> listarTodos() {
        return postRepository.findAll();
    }

    public List<Post> listarPorArtista(Long artistaId) {
        return postRepository.findByArtistaId(artistaId);
    }

    // ===========================
    // OBTENER POR ID
    // ===========================
    public Post obtenerPorId(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));
    }

    // ===========================
    // EDITAR POST
    // ===========================
    @Transactional
    public Post editarPost(Long postId, Post nuevosDatos) {

        Post post = obtenerPorId(postId);

        post.setTitulo(nuevosDatos.getTitulo());
        post.setContenido(nuevosDatos.getContenido());
        post.setFechaPublicacion(LocalDateTime.now());

        // Si cambia la imagen
        if (nuevosDatos.getImagenUrl() != null && !nuevosDatos.getImagenUrl().isBlank()) {
            post.setImagenUrl(nuevosDatos.getImagenUrl());
        }

        return postRepository.save(post);
    }

    // ===========================
    // ELIMINAR POST
    // ===========================
    @Transactional
    public void eliminarPost(Long id) {
        postRepository.deleteById(id);
    }

}