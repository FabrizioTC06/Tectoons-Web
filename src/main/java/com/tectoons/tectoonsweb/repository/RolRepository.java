/*package com.tectoons.tectoonsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tectoons.tectoonsweb.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}*/

package com.tectoons.tectoonsweb.repository;

import com.tectoons.tectoonsweb.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
