/*package com.tectoons.tectoonsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tectoons.tectoonsweb.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    boolean existsByUsuarioIdAndObraId(Integer usuarioId, Integer obraId);
    int countByObraId(Integer obraId);
}
*/
/*package com.tectoons.tectoonsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tectoons.tectoonsweb.model.Like;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUsuarioAndObra(Usuario usuario, Obra obra);
    List<Like> findAllByUsuario(Usuario usuario);
    boolean existsByObraIdAndUsuarioId(Long obraId, Long usuarioId);
}
*/

package com.tectoons.tectoonsweb.repository;

import com.tectoons.tectoonsweb.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByObraIdAndUsuarioId(Long obraId, Long usuarioId);

    Optional<Like> findByObraIdAndUsuarioId(Long obraId, Long usuarioId);

    long countByObraId(Long obraId);

    // 🔥 NECESARIO para obtener todas las obras con like del usuario
    List<Like> findByUsuarioId(Long usuarioId);
}
