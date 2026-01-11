/*package com.tectoons.tectoonsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tectoons.tectoonsweb.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByDni(String dni);
}

*/

/*package com.tectoons.tectoonsweb.repository;

import com.tectoons.tectoonsweb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByCorreo(String correo);
    boolean existsByNombreUsuario(String nombreUsuario);
}*/

/*package com.tectoons.tectoonsweb.repository;

import com.tectoons.tectoonsweb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByDni(String dni);

    boolean existsByCorreo(String correo);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByDni(String dni);
}*/


package com.tectoons.tectoonsweb.repository;

import com.tectoons.tectoonsweb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByCorreo(String correo);
    boolean existsByNombreUsuario(String nombreUsuario);
    /*boolean existsByDni(String dni);*/
    
}




