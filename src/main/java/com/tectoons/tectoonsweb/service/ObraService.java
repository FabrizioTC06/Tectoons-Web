/*package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.repository.ObraRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ObraService {

    @Autowired
    private ObraRepository obraRepository;

    // === CRUD BÁSICO ===
    public List<Obra> listarTodos() {
        return obraRepository.findAll();
    }

    public Optional<Obra> obtenerPorId(Long id) {
        return obraRepository.findById(id);
    }

    public Obra guardar(Obra obra) {
        return obraRepository.save(obra);
    }

    public void eliminar(Long id) {
        obraRepository.deleteById(id);
    }

    public List<Obra> buscarPorArtista(Long artistaId) {
        return obraRepository.findByArtistaId(artistaId);
    }

    // === BÚSQUEDA, FILTRO Y ORDENAMIENTO ===
    public List<Obra> buscarFiltrarOrdenar(String texto, String categoria, String orden) {
        boolean tieneFiltro = categoria != null && !categoria.equalsIgnoreCase("all") && !categoria.isEmpty();
        boolean tieneBusqueda = texto != null && !texto.isEmpty();

        // CASO: FILTRO + BUSQUEDA
        if (tieneFiltro && tieneBusqueda) {
            return obraRepository.findByCategoriaIgnoreCaseAndTituloContainingIgnoreCase(categoria, texto);
        }

        // CASO: SOLO FILTRO
        if (tieneFiltro) {
            switch (orden != null ? orden : "") {
                case "tituloAsc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByTituloAsc(categoria);
                case "tituloDesc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByTituloDesc(categoria);
                case "autorAsc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByArtista_NombreArtisticoAsc(categoria);
                case "autorDesc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByArtista_NombreArtisticoDesc(categoria);
                case "fechaAsc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByFechaPublicacionAsc(categoria);
                case "fechaDesc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByFechaPublicacionDesc(categoria);
                case "likesDesc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByLikesDesc(categoria);
                default:
                    return obraRepository.findByCategoriaIgnoreCase(categoria);
            }
        }

        // CASO: SOLO BUSQUEDA
        if (tieneBusqueda) {
            return obraRepository.findByTituloContainingIgnoreCaseOrArtista_NombreArtisticoContainingIgnoreCase(texto,
                    texto);
        }

        // CASO: SIN FILTRO NI BUSQUEDA, solo ordenamiento global
        switch (orden != null ? orden : "") {
            case "tituloAsc":
                return obraRepository.findAllByOrderByTituloAsc();
            case "tituloDesc":
                return obraRepository.findAllByOrderByTituloDesc();
            case "autorAsc":
                return obraRepository.findAllByOrderByArtista_NombreArtisticoAsc();
            case "autorDesc":
                return obraRepository.findAllByOrderByArtista_NombreArtisticoDesc();
            case "fechaAsc":
                return obraRepository.findAllByOrderByFechaPublicacionAsc();
            case "fechaDesc":
                return obraRepository.findAllByOrderByFechaPublicacionDesc();
            case "likesDesc":
                return obraRepository.findAllByOrderByLikesDesc();
            default:
                return obraRepository.findAll();
        }
    }
}*/


package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.repository.ObraRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObraService {

    private final ObraRepository obraRepository;

    public ObraService(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }

    public List<Obra> listarTodos() {
        return obraRepository.findAll();
    }

    public Optional<Obra> obtenerPorId(Long id) {
        return obraRepository.findById(id);
    }

    @Transactional
    public Obra guardar(Obra obra) {
        return obraRepository.save(obra);
    }

    @Transactional
    public void eliminar(Long id) {
        obraRepository.deleteById(id);
    }

    public List<Obra> buscarPorArtista(Long artistaId) {
        return obraRepository.findByArtistaId(artistaId);
    }

    public List<Obra> buscarFiltrarOrdenar(String texto, String categoria, String orden) {
        boolean tieneFiltro = categoria != null && !categoria.equalsIgnoreCase("all") && !categoria.isEmpty();
        boolean tieneBusqueda = texto != null && !texto.isEmpty();

        if (tieneFiltro && tieneBusqueda) {
            return obraRepository.findByCategoriaIgnoreCaseAndTituloContainingIgnoreCase(categoria, texto);
        }

        if (tieneFiltro) {
            switch (orden != null ? orden : "") {
                case "tituloAsc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByTituloAsc(categoria);
                case "tituloDesc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByTituloDesc(categoria);
                case "autorAsc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByArtista_NombreArtisticoAsc(categoria);
                case "autorDesc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByArtista_NombreArtisticoDesc(categoria);
                case "fechaAsc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByFechaPublicacionAsc(categoria);
                case "fechaDesc":
                    return obraRepository.findByCategoriaIgnoreCaseOrderByFechaPublicacionDesc(categoria);
                //case "likesDesc": return obraRepository.findByCategoriaIgnoreCaseOrderByLikesDesc(categoria);
                default:
                    return obraRepository.findByCategoriaIgnoreCase(categoria);
            }
        }

        if (tieneBusqueda) {
            return obraRepository.findByTituloContainingIgnoreCaseOrArtista_NombreArtisticoContainingIgnoreCase(texto, texto);
        }

        switch (orden != null ? orden : "") {
            case "tituloAsc":
                return obraRepository.findAllByOrderByTituloAsc();
            case "tituloDesc":
                return obraRepository.findAllByOrderByTituloDesc();
            case "autorAsc":
                return obraRepository.findAllByOrderByArtista_NombreArtisticoAsc();
            case "autorDesc":
                return obraRepository.findAllByOrderByArtista_NombreArtisticoDesc();
            case "fechaAsc":
                return obraRepository.findAllByOrderByFechaPublicacionAsc();
            case "fechaDesc":
                return obraRepository.findAllByOrderByFechaPublicacionDesc();
            //case "likesDesc": return obraRepository.findAllByOrderByLikesDesc();
            default:
                return obraRepository.findAll();
        }
    }
}

/*package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.repository.ObraRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObraService {

    private final ObraRepository obraRepository;

    public ObraService(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }

    // Listar todas las obras
    public List<Obra> listarTodos() {
        return obraRepository.findAll();
    }

    // Obtener obra por ID
    public Optional<Obra> obtenerPorId(Long id) {
        return obraRepository.findById(id);
    }

    // Guardar obra
    @Transactional
    public Obra guardar(Obra obra) {
        return obraRepository.save(obra);
    }

    // Eliminar obra
    @Transactional
    public void eliminar(Long id) {
        obraRepository.deleteById(id);
    }

    // Buscar obras por artista
    public List<Obra> buscarPorArtista(Long artistaId) {
        return obraRepository.findByArtistaId(artistaId);
    }

    // Filtrar, buscar y ordenar obras
    public List<Obra> buscarFiltrarOrdenar(String texto, String categoria, String orden) {
        boolean tieneFiltro = categoria != null && !categoria.equalsIgnoreCase("all") && !categoria.isEmpty();
        boolean tieneBusqueda = texto != null && !texto.isEmpty();

        // Caso: filtro por categoría + búsqueda por texto
        if (tieneFiltro && tieneBusqueda) {
            // Filtrar por categoría y título
            return obraRepository.findDistinctByCategorias_NombreIgnoreCaseAndTituloContainingIgnoreCase(categoria, texto);
            // Si quieres filtrar también por nombre de artista:
            // return obraRepository.findDistinctByCategorias_NombreIgnoreCaseAndArtista_NombreArtisticoContainingIgnoreCase(categoria, texto);
        }

        // Caso: solo filtro por categoría
        if (tieneFiltro) {
            switch (orden != null ? orden : "") {
                case "tituloAsc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByTituloAsc(categoria);
                case "tituloDesc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByTituloDesc(categoria);
                case "autorAsc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByArtista_NombreArtisticoAsc(categoria);
                case "autorDesc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByArtista_NombreArtisticoDesc(categoria);
                case "fechaAsc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByFechaPublicacionAsc(categoria);
                case "fechaDesc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByFechaPublicacionDesc(categoria);
                default:
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCase(categoria);
            }
        }

        // Caso: solo búsqueda por texto
        if (tieneBusqueda) {
            return obraRepository.findByTituloContainingIgnoreCaseOrArtista_NombreArtisticoContainingIgnoreCase(texto, texto);
        }

        // Caso: ordenamiento general sin categoría ni búsqueda
        switch (orden != null ? orden : "") {
            case "tituloAsc":
                return obraRepository.findAllByOrderByTituloAsc();
            case "tituloDesc":
                return obraRepository.findAllByOrderByTituloDesc();
            case "autorAsc":
                return obraRepository.findAllByOrderByArtista_NombreArtisticoAsc();
            case "autorDesc":
                return obraRepository.findAllByOrderByArtista_NombreArtisticoDesc();
            case "fechaAsc":
                return obraRepository.findAllByOrderByFechaPublicacionAsc();
            case "fechaDesc":
                return obraRepository.findAllByOrderByFechaPublicacionDesc();
            default:
                return obraRepository.findAll();
        }
    }
}*/

/*package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Categoria;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.repository.CategoriaRepository;
import com.tectoons.tectoonsweb.repository.ObraRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ObraService {

    private final ObraRepository obraRepository;
    private final CategoriaRepository categoriaRepository;

    public ObraService(ObraRepository obraRepository,
                       CategoriaRepository categoriaRepository) {
        this.obraRepository = obraRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Listar todas las obras
    public List<Obra> listarTodos() {
        return obraRepository.findAll();
    }

    // Obtener obra por ID
    public Optional<Obra> obtenerPorId(Long id) {
        return obraRepository.findById(id);
    }

    // Guardar obra normal (sin categorías)
    @Transactional
    public Obra guardar(Obra obra) {
        return obraRepository.save(obra);
    }

    // Guardar obra + asignar categorías
    @Transactional
    public Obra guardarConCategorias(Obra obra, List<Long> categoriaIds) {

        Set<Categoria> nuevasCategorias = new HashSet<>();

        if (categoriaIds != null) {
            for (Long catId : categoriaIds) {
                categoriaRepository.findById(catId).ifPresent(nuevasCategorias::add);
            }
        }

        obra.setCategorias(nuevasCategorias);

        return obraRepository.save(obra);
    }

    // Eliminar obra
    @Transactional
    public void eliminar(Long id) {
        obraRepository.deleteById(id);
    }

    // Buscar obras por artista
    public List<Obra> buscarPorArtista(Long artistaId) {
        return obraRepository.findByArtistaId(artistaId);
    }

    // Filtrar, buscar y ordenar obras
    public List<Obra> buscarFiltrarOrdenar(String texto, String categoria, String orden) {
        boolean tieneFiltro = categoria != null && !categoria.equalsIgnoreCase("all") && !categoria.isEmpty();
        boolean tieneBusqueda = texto != null && !texto.isEmpty();

        // Caso: filtro por categoría + búsqueda por texto
        if (tieneFiltro && tieneBusqueda) {
            return obraRepository
                    .findDistinctByCategorias_NombreIgnoreCaseAndTituloContainingIgnoreCase(categoria, texto);
        }

        // Caso: solo filtro por categoría
        if (tieneFiltro) {
            switch (orden != null ? orden : "") {
                case "tituloAsc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByTituloAsc(categoria);
                case "tituloDesc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByTituloDesc(categoria);
                case "autorAsc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByArtista_NombreArtisticoAsc(categoria);
                case "autorDesc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByArtista_NombreArtisticoDesc(categoria);
                case "fechaAsc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByFechaPublicacionAsc(categoria);
                case "fechaDesc":
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCaseOrderByFechaPublicacionDesc(categoria);
                default:
                    return obraRepository.findDistinctByCategorias_NombreIgnoreCase(categoria);
            }
        }

        // Caso: solo búsqueda por texto
        if (tieneBusqueda) {
            return obraRepository
                    .findByTituloContainingIgnoreCaseOrArtista_NombreArtisticoContainingIgnoreCase(texto, texto);
        }

        // Caso: ordenamiento general sin filtros
        switch (orden != null ? orden : "") {
            case "tituloAsc":
                return obraRepository.findAllByOrderByTituloAsc();
            case "tituloDesc":
                return obraRepository.findAllByOrderByTituloDesc();
            case "autorAsc":
                return obraRepository.findAllByOrderByArtista_NombreArtisticoAsc();
            case "autorDesc":
                return obraRepository.findAllByOrderByArtista_NombreArtisticoDesc();
            case "fechaAsc":
                return obraRepository.findAllByOrderByFechaPublicacionAsc();
            case "fechaDesc":
                return obraRepository.findAllByOrderByFechaPublicacionDesc();
            default:
                return obraRepository.findAll();
        }
    }
}*/






/*
 * @Autowired
 * private ObraRepository obraRepository;
 * 
 * 
 * 
 * // === FILTROS ===
 * public List<Obra> filtrarPorCategoria(String categoria) {
 * return obraRepository.findByCategoriaIgnoreCase(categoria);
 * }
 * 
 * // === ORDENAMIENTOS ===
 * public List<Obra> ordenarPorTituloAsc() {
 * return obraRepository.findAllByOrderByTituloAsc();
 * }
 * 
 * public List<Obra> ordenarPorTituloDesc() {
 * return obraRepository.findAllByOrderByTituloDesc();
 * }
 * 
 * public List<Obra> ordenarPorFechaRecientes() {
 * return obraRepository.findAllByOrderByFechaPublicacionDesc();
 * }
 * 
 * public List<Obra> ordenarPorFechaAntiguas() {
 * return obraRepository.findAllByOrderByFechaPublicacionAsc();
 * }
 * 
 * public List<Obra> ordenarPorLikes() {
 * return obraRepository.findAllByOrderByLikesDesc();
 * }
 * 
 * // === BÚSQUEDAS ===
 * public List<Obra> buscarPorTexto(String texto) {
 * return obraRepository.
 * findByTituloContainingIgnoreCaseOrArtista_NombreArtisticoContainingIgnoreCase
 * (texto, texto);
 * }
 * 
 * // === COMBINACIONES ===
 * public List<Obra> buscarPorCategoriaYTexto(String categoria, String texto) {
 * return obraRepository.findByCategoriaIgnoreCaseAndTituloContainingIgnoreCase(
 * categoria, texto);
 * }
 * 
 * public List<Obra> buscarPorCategoriaYAutor(String categoria, String autor) {
 * return obraRepository.
 * findByCategoriaIgnoreCaseAndArtista_NombreArtisticoContainingIgnoreCase(
 * categoria, autor);
 * }
 * 
 * public List<Obra> filtrarCategoriaOrdenarPorAZ(String categoria) {
 * return obraRepository.findByCategoriaIgnoreCaseOrderByTituloAsc(categoria);
 * }
 * 
 * public List<Obra> filtrarCategoriaOrdenarPorRecientes(String categoria) {
 * return
 * obraRepository.findByCategoriaIgnoreCaseOrderByFechaPublicacionDesc(categoria
 * );
 * }
 * 
 * public List<Obra> filtrarCategoriaOrdenarPorLikes(String categoria) {
 * return obraRepository.findByCategoriaIgnoreCaseOrderByLikesDesc(categoria);
 * }
 * 
 * // === COMBINACIÓN GLOBAL (opcional, flexible) ===
 * public List<Obra> buscarFiltrarYOrdenar(String texto, String categoria,
 * String orden) {
 * List<Obra> obras;
 * 
 * // 🔍 Búsqueda base
 * if (texto != null && !texto.isEmpty() && categoria != null &&
 * !categoria.isEmpty()) {
 * obras = buscarPorCategoriaYTexto(categoria, texto);
 * } else if (categoria != null && !categoria.isEmpty()) {
 * obras = filtrarPorCategoria(categoria);
 * } else if (texto != null && !texto.isEmpty()) {
 * obras = buscarPorTexto(texto);
 * } else {
 * obras = listarTodos();
 * }
 * 
 * // 🔠 Aplicar ordenamiento final
 * switch (orden) {
 * case "az":
 * obras.sort((a, b) -> a.getTitulo().compareToIgnoreCase(b.getTitulo()));
 * break;
 * case "za":
 * obras.sort((a, b) -> b.getTitulo().compareToIgnoreCase(a.getTitulo()));
 * break;
 * case "recientes":
 * obras.sort((a, b) ->
 * b.getFechaPublicacion().compareTo(a.getFechaPublicacion()));
 * break;
 * case "antiguas":
 * obras.sort((a, b) ->
 * a.getFechaPublicacion().compareTo(b.getFechaPublicacion()));
 * break;
 * case "likes":
 * obras.sort((a, b) -> Integer.compare(b.getLikes(), a.getLikes()));
 * break;
 * }
 * 
 * return obras;
 * }
 */