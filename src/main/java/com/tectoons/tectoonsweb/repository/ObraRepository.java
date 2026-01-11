/*package com.tectoons.tectoonsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tectoons.tectoonsweb.model.Obra;
import java.util.List;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {
List<Obra> findByArtista_Usuario_Id(Long usuarioId);

    // Buscar obras por ID de artista
    List<Obra> findByArtistaId(Long artistaId);

    // Búsqueda por título o autor
    List<Obra> findByTituloContainingIgnoreCaseOrArtista_NombreArtisticoContainingIgnoreCase(String titulo,
            String nombreArtistico);

    // Filtrar por categoría
    List<Obra> findByCategoriaIgnoreCase(String categoria);

    // Ordenamiento básico
    List<Obra> findAllByOrderByTituloAsc();

    List<Obra> findAllByOrderByTituloDesc();

    List<Obra> findAllByOrderByArtista_NombreArtisticoAsc();

    List<Obra> findAllByOrderByArtista_NombreArtisticoDesc();

    List<Obra> findAllByOrderByFechaPublicacionDesc();

    List<Obra> findAllByOrderByFechaPublicacionAsc();

    List<Obra> findAllByOrderByLikesDesc();

    // Combinaciones por categoría y orden
    List<Obra> findByCategoriaIgnoreCaseOrderByTituloAsc(String categoria);

    List<Obra> findByCategoriaIgnoreCaseOrderByTituloDesc(String categoria);

    List<Obra> findByCategoriaIgnoreCaseOrderByArtista_NombreArtisticoAsc(String categoria);

    List<Obra> findByCategoriaIgnoreCaseOrderByArtista_NombreArtisticoDesc(String categoria);

    List<Obra> findByCategoriaIgnoreCaseOrderByFechaPublicacionDesc(String categoria);

    List<Obra> findByCategoriaIgnoreCaseOrderByFechaPublicacionAsc(String categoria);

    List<Obra> findByCategoriaIgnoreCaseOrderByLikesDesc(String categoria);

    // Búsqueda dentro de categoría
    List<Obra> findByCategoriaIgnoreCaseAndTituloContainingIgnoreCase(String categoria, String titulo);

    List<Obra> findByCategoriaIgnoreCaseAndArtista_NombreArtisticoContainingIgnoreCase(String categoria, String autor);
}*/



package com.tectoons.tectoonsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tectoons.tectoonsweb.model.Obra;
import java.util.List;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {
    List<Obra> findByArtistaId(Long artistaId);
    List<Obra> findByTituloContainingIgnoreCaseOrArtista_NombreArtisticoContainingIgnoreCase(String titulo, String nombreArtistico);
    
    List<Obra> findByCategoriaIgnoreCase(String categoria);
    List<Obra> findAllByOrderByTituloAsc();
    List<Obra> findAllByOrderByTituloDesc();
    List<Obra> findAllByOrderByArtista_NombreArtisticoAsc();
    List<Obra> findAllByOrderByArtista_NombreArtisticoDesc();
    List<Obra> findAllByOrderByFechaPublicacionDesc();
    List<Obra> findAllByOrderByFechaPublicacionAsc();
    //List<Obra> findAllByOrderByLikesDesc();

    List<Obra> findByCategoriaIgnoreCaseOrderByTituloAsc(String categoria);
    List<Obra> findByCategoriaIgnoreCaseOrderByTituloDesc(String categoria);
    List<Obra> findByCategoriaIgnoreCaseOrderByArtista_NombreArtisticoAsc(String categoria);
    List<Obra> findByCategoriaIgnoreCaseOrderByArtista_NombreArtisticoDesc(String categoria);
    List<Obra> findByCategoriaIgnoreCaseOrderByFechaPublicacionDesc(String categoria);
    List<Obra> findByCategoriaIgnoreCaseOrderByFechaPublicacionAsc(String categoria);
    //List<Obra> findByCategoriaIgnoreCaseOrderByLikesDesc(String categoria);

    List<Obra> findByCategoriaIgnoreCaseAndTituloContainingIgnoreCase(String categoria, String titulo);
    List<Obra> findByCategoriaIgnoreCaseAndArtista_NombreArtisticoContainingIgnoreCase(String categoria, String autor);
}

/*package com.tectoons.tectoonsweb.repository;

import com.tectoons.tectoonsweb.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {

    // Buscar obras por artista
    List<Obra> findByArtistaId(Long artistaId);

    // Buscar por título o nombre del artista (texto)
    List<Obra> findByTituloContainingIgnoreCaseOrArtista_NombreArtisticoContainingIgnoreCase(String titulo, String nombreArtistico);

    // BUSCAR POR CATEGORÍA (ahora usando la relación ManyToMany)
    List<Obra> findDistinctByCategorias_NombreIgnoreCase(String nombreCategoria);

    List<Obra> findDistinctByCategorias_NombreIgnoreCaseOrderByTituloAsc(String nombreCategoria);
    List<Obra> findDistinctByCategorias_NombreIgnoreCaseOrderByTituloDesc(String nombreCategoria);
    List<Obra> findDistinctByCategorias_NombreIgnoreCaseOrderByArtista_NombreArtisticoAsc(String nombreCategoria);
    List<Obra> findDistinctByCategorias_NombreIgnoreCaseOrderByArtista_NombreArtisticoDesc(String nombreCategoria);
    List<Obra> findDistinctByCategorias_NombreIgnoreCaseOrderByFechaPublicacionAsc(String nombreCategoria);
    List<Obra> findDistinctByCategorias_NombreIgnoreCaseOrderByFechaPublicacionDesc(String nombreCategoria);

    // Filtrar por categoría + texto en título o nombre del artista
    List<Obra> findDistinctByCategorias_NombreIgnoreCaseAndTituloContainingIgnoreCase(String nombreCategoria, String titulo);
    List<Obra> findDistinctByCategorias_NombreIgnoreCaseAndArtista_NombreArtisticoContainingIgnoreCase(String nombreCategoria, String nombreArtistico);

    // Ordenamientos generales sin categoría
    List<Obra> findAllByOrderByTituloAsc();
    List<Obra> findAllByOrderByTituloDesc();
    List<Obra> findAllByOrderByArtista_NombreArtisticoAsc();
    List<Obra> findAllByOrderByArtista_NombreArtisticoDesc();
    List<Obra> findAllByOrderByFechaPublicacionAsc();
    List<Obra> findAllByOrderByFechaPublicacionDesc();

    //List<Obra> findAllByOrderByLikesDesc();
    //List<Obra> findAllByOrderByLikesAsc();
    //List<Obra> findByCategoriaIgnoreCaseOrderByLikesDesc(String categoria);
    //List<Obra> findByCategoriaIgnoreCaseOrderByLikesAsc(String categoria);
    //List<Obra> findDistinctByCategorias_NombreIgnoreCaseOrderByLikesDesc(String nombreCategoria);
    //List<Obra> findDistinctByCategorias_NombreIgnoreCaseOrderByLikesAsc(String nombreCategoria);

}*/

