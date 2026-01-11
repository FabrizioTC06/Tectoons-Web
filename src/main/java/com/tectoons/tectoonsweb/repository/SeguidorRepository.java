/*package com.tectoons.tectoonsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tectoons.tectoonsweb.model.Seguidor;
import com.tectoons.tectoonsweb.model.Usuario;

import java.util.List;

@Repository
public interface SeguidorRepository extends JpaRepository<Seguidor, Long> {
    boolean existsBySeguidorIdAndSeguidoId(Integer seguidorId, Integer seguidoId);
    List<Seguidor> findBySeguidoId(Integer seguidoId);
    List<Seguidor> findBySeguidorId(Integer seguidorId);
    boolean existsBySeguidor_IdAndSeguido_Id(Integer seguidorId, Integer seguidoId);
List<Seguidor> findBySeguidor_Id(Integer seguidorId);
List<Seguidor> findBySeguido_Id(Integer seguidoId);
boolean existsBySeguidorAndSeguido(Usuario seguidor, Usuario seguido);

}*/

/*package com.tectoons.tectoonsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tectoons.tectoonsweb.model.Seguidor;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeguidorRepository extends JpaRepository<Seguidor, Long> {
    boolean existsBySeguidor_IdAndSeguido_Id(Long seguidorId, Long seguidoId);
    Optional<Seguidor> findBySeguidor_IdAndSeguido_Id(Long seguidorId, Long seguidoId);
    List<Seguidor> findBySeguido_Id(Long seguidoId);
    List<Seguidor> findBySeguidor_Id(Long seguidorId);
    // borrar relación
    void deleteBySeguidor_IdAndSeguido_Id(Long seguidorId, Long seguidoId);

    // contar seguidores de un artista
    long countBySeguido_Id(Long seguidoId);
}

*/


package com.tectoons.tectoonsweb.repository;

import com.tectoons.tectoonsweb.model.Seguidor;
import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface SeguidorRepository extends JpaRepository<Seguidor, Long> {

    // Buscar si un usuario ya sigue a un artista
    Optional<Seguidor> findBySeguidorAndSeguido(Usuario seguidor, Artista seguido);

    // Listar todos los seguidores de un artista
    List<Seguidor> findBySeguido(Artista seguido);

    // Listar todos los artistas que sigue un usuario
    List<Seguidor> findBySeguidor(Usuario seguidor);

    // Contar seguidores de un artista
    long countBySeguido(Artista seguido);

    // Métodos necesarios para toggleFollow
    boolean existsBySeguidor_IdAndSeguido_Id(Long seguidorId, Long seguidoId);

    Optional<Seguidor> findBySeguidor_IdAndSeguido_Id(Long seguidorId, Long seguidoId);

    void deleteBySeguidor_IdAndSeguido_Id(Long seguidorId, Long seguidoId);

    // Para los métodos del service usando _Id (derivados de Spring Data)
    List<Seguidor> findBySeguido_Id(Long seguidoId);
    List<Seguidor> findBySeguidor_Id(Long seguidorId);
    long countBySeguido_Id(Long seguidoId);
}


