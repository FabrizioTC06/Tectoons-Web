/*package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
}
*/

/*package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Rol;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.RolRepository;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    // Guardar usuario con roles
    public Usuario registrarUsuario(Usuario usuario, String rolNombre) throws Exception {
        // Validaciones únicas
        if (usuarioRepository.findByDni(usuario.getDni()).isPresent()) {
            throw new Exception("El DNI ya está registrado");
        }
        if (usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()).isPresent()) {
            throw new Exception("El nombre de usuario ya está registrado");
        }
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new Exception("El correo ya está registrado");
        }

        // Asignar rol
        Rol rol = rolRepository.findByNombre(rolNombre);
        if (rol == null) {
            throw new Exception("Rol no encontrado");
        }
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }

    // Verificar login
    public Optional<Usuario> login(String username, String contrasena) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreUsuario(username);
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioRepository.findByCorreo(username);
        }
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioRepository.findByDni(username);
        }
        if (usuarioOpt.isPresent() && usuarioOpt.get().getContrasena().equals(contrasena)) {
            return usuarioOpt;
        }
        return Optional.empty();
    }
}
*/
/*
package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public Optional<Usuario> buscarPorDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
*/

/*package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.model.Rol;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import com.tectoons.tectoonsweb.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        // Asignar rol por defecto
        Rol rolUser = rolRepository.findByNombre("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
        usuario.addRol(rolUser);

        // Guardar usuario
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> login(String identificador, String contrasena) {
        // Buscar por correo o nombre de usuario
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(identificador);
        if (usuario.isEmpty()) {
            usuario = usuarioRepository.findByNombreUsuario(identificador);
        }

        // Validar contraseña
        if (usuario.isPresent() && usuario.get().getContrasena().equals(contrasena)) {
            return usuario;
        }
        return Optional.empty();
    }

    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
}
*/

/*package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.model.Rol;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import com.tectoons.tectoonsweb.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    // Registrar usuario
    public Usuario registrarUsuario(Usuario usuario) {
        // Asignar rol por defecto
        Rol rolUser = rolRepository.findByNombre("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
        usuario.addRol(rolUser);

        // Guardar usuario tal cual (sin encriptar contraseña)
        return usuarioRepository.save(usuario);
    }

    // Login por correo o nombreUsuario
    public Optional<Usuario> login(String identificador, String contrasena) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(identificador);
        if (usuario.isEmpty()) {
            usuario = usuarioRepository.findByNombreUsuario(identificador);
        }

        // Validar contraseña directamente (sin encriptar)
        if (usuario.isPresent() && usuario.get().getContrasena().equals(contrasena)) {
            return usuario;
        }
        return Optional.empty();
    }

    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
}
*/

/*Version anterior a la final

package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean existePorCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existePorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public void registrar(Usuario usuario) {
        // No se encripta, se guarda tal cual
        usuarioRepository.save(usuario);
    }

    public Usuario iniciarSesion(String identificador, String contrasena) {
        // Buscar por correo o nombreUsuario
        return usuarioRepository.findByCorreo(identificador)
                .or(() -> usuarioRepository.findByNombreUsuario(identificador))
                .filter(u -> u.getContrasena().equals(contrasena))
                .orElse(null);
    }
}*/

/*package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Rol;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.tectoons.tectoonsweb.repository.RolRepository;

import java.util.Optional;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existePorDni(String dni) {
        return usuarioRepository.existsByDni(dni);
    }

    public boolean existePorCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existePorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    @Transactional
    public void registrar(Usuario usuario) {

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Asigna rol por defecto
        Rol rolUsuario = rolRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new RuntimeException("Rol USUARIO no existe"));

        usuario.getRoles().add(rolUsuario);

        usuarioRepository.save(usuario);
    }

    public Usuario iniciarSesion(String identificador, String contrasena) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(identificador)
                .or(() -> usuarioRepository.findByNombreUsuario(identificador));

        if (usuarioOpt.isPresent() && usuarioOpt.get().getContrasena().equals(contrasena)) {
            return usuarioOpt.get();
        }
        return null; // credenciales incorrectas
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}*/

/*package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Rol;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.RolRepository;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //public boolean existePorDni(String dni) {
    //    return usuarioRepository.existsByDni(dni);
    //}

    public boolean existePorCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existePorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    @Transactional
    public void registrar(Usuario usuario) {

        if (existePorCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        if (existePorNombreUsuario(usuario.getNombreUsuario())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        //if (existePorDni(usuario.getDni())) {
        //   throw new IllegalArgumentException("El DNI ya está registrado");
        //}

        // 🔐 Encriptar contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // 🔐 Asignar rol por defecto USUARIO
        Rol rolUsuario = rolRepository.findByNombre("ROLE_USUARIO")
                .orElseThrow(() -> new RuntimeException("El rol ROLE_USUARIO no existe en la BD"));
        usuario.getRoles().add(rolUsuario);

        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario buscarPorIdentificador(String identificador) {

        // Buscar por correo
        Optional<Usuario> usuarioCorreo = usuarioRepository.findByCorreo(identificador);
        if (usuarioCorreo.isPresent()) {
            return usuarioCorreo.get();
        }

        // Buscar por nombre de usuario
        Optional<Usuario> usuarioNombre = usuarioRepository.findByNombreUsuario(identificador);
        if (usuarioNombre.isPresent()) {
            return usuarioNombre.get();
        }

        throw new RuntimeException("Usuario no encontrado con identificador: " + identificador);
    }

    // 🔷 Agregar rol ARTISTA
    public void asignarRolArtista(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rolArtista = rolRepository.findByNombre("ROLE_ARTISTA")
                .orElseThrow(() -> new RuntimeException("El rol ROLE_ARTISTA no existe en la BD"));

        usuario.getRoles().add(rolArtista);
        usuarioRepository.save(usuario);
    }

    // 🔶 Agregar rol ADMIN
    public void asignarRolAdmin(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("El rol ROLE_ADMIN no existe en la BD"));

        usuario.getRoles().add(rolAdmin);
        usuarioRepository.save(usuario);
    }

}*/
/*Rol rolUsuario = rolRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new RuntimeException("El rol USUARIO no existe en la BD"));
        usuario.getRoles().add(rolUsuario);

        usuarioRepository.save(usuario); */

/*package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Rol;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.RolRepository;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ========================
    // REGISTRO DE USUARIO
    // ========================
    @Transactional
    public void registrar(Usuario usuario) {
        if (existePorCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        if (existePorNombreUsuario(usuario.getNombreUsuario())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        // Encriptar contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Asignar rol USUARIO por defecto
        Rol rolUsuario = rolRepository.findByNombre("ROLE_USUARIO")
                .orElseThrow(() -> new RuntimeException("El rol ROLE_USUARIO no existe en la BD"));
        usuario.getRoles().add(rolUsuario);

        usuarioRepository.save(usuario);
    }

    // ========================
    // MÉTODOS DE BÚSQUEDA
    // ========================
    public boolean existePorCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existePorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario buscarPorIdentificador(String identificador) {
        Optional<Usuario> usuarioCorreo = usuarioRepository.findByCorreo(identificador);
        if (usuarioCorreo.isPresent()) return usuarioCorreo.get();

        Optional<Usuario> usuarioNombre = usuarioRepository.findByNombreUsuario(identificador);
        if (usuarioNombre.isPresent()) return usuarioNombre.get();

        throw new RuntimeException("Usuario no encontrado con identificador: " + identificador);
    }

    // ========================
    // ACTUALIZAR PERFIL
    // ========================
    @Transactional
    public void actualizarPerfil(Long usuarioId, Usuario datos) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreCompleto(datos.getNombreCompleto());
        usuario.setNombreUsuario(datos.getNombreUsuario());
        usuario.setCorreo(datos.getCorreo());
        usuario.setFechaNacimiento(datos.getFechaNacimiento());
        usuario.setFotoPerfil(datos.getFotoPerfil());
        usuario.setDescripcion(datos.getDescripcion());

        if (datos.getContrasena() != null && !datos.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(datos.getContrasena()));
        }

        usuarioRepository.save(usuario);
    }

    // ========================
    // ASIGNAR ROL ARTISTA
    // ========================
    @Transactional
    public void asignarRolArtista(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rolArtista = rolRepository.findByNombre("ROLE_ARTISTA")
                .orElseThrow(() -> new RuntimeException("El rol ROLE_ARTISTA no existe en la BD"));

        usuario.getRoles().add(rolArtista);
        usuarioRepository.save(usuario);
    }

    // ========================
    // ASIGNAR ROL ADMIN
    // ========================
    @Transactional
    public void asignarRolAdmin(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("El rol ROLE_ADMIN no existe en la BD"));

        usuario.getRoles().add(rolAdmin);
        usuarioRepository.save(usuario);
    }
}*/

package com.tectoons.tectoonsweb.service;

import com.tectoons.tectoonsweb.model.Rol;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.RolRepository;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ========================
    // REGISTRO DE USUARIO
    // ========================
    @Transactional
    public void registrar(Usuario usuario) {
        if (existePorCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        if (existePorNombreUsuario(usuario.getNombreUsuario())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        Rol rolUsuario = rolRepository.findByNombre("ROLE_USUARIO")
                .orElseThrow(() -> new RuntimeException("El rol ROLE_USUARIO no existe en la BD"));
        usuario.getRoles().add(rolUsuario);

        usuarioRepository.save(usuario);
    }

    // ========================
    // MÉTODOS DE BÚSQUEDA
    // ========================
    public boolean existePorCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existePorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario buscarPorIdentificador(String identificador) {
        Optional<Usuario> usuarioCorreo = usuarioRepository.findByCorreo(identificador);
        if (usuarioCorreo.isPresent())
            return usuarioCorreo.get();

        Optional<Usuario> usuarioNombre = usuarioRepository.findByNombreUsuario(identificador);
        if (usuarioNombre.isPresent())
            return usuarioNombre.get();

        throw new RuntimeException("Usuario no encontrado con identificador: " + identificador);
    }

    // ========================
    // ACTUALIZAR PERFIL
    // ========================
    @Transactional
    public void actualizarPerfil(Long usuarioId, Usuario datos) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Campos obligatorios
        usuario.setNombreCompleto(datos.getNombreCompleto());
        usuario.setNombreUsuario(datos.getNombreUsuario());
        usuario.setCorreo(datos.getCorreo());

        // Campos opcionales
        usuario.setFechaNacimiento(datos.getFechaNacimiento() != null ? datos.getFechaNacimiento() : null);
        usuario.setUbicacion(
                (datos.getUbicacion() != null && !datos.getUbicacion().isBlank()) ? datos.getUbicacion() : null);
        usuario.setDescripcion(
                (datos.getDescripcion() != null && !datos.getDescripcion().isBlank()) ? datos.getDescripcion() : null);
        usuario.setFotoPerfil(
                (datos.getFotoPerfil() != null && !datos.getFotoPerfil().isBlank()) ? datos.getFotoPerfil() : null);
        usuario.setBannerPerfil(
                (datos.getBannerPerfil() != null && !datos.getBannerPerfil().isBlank()) ? datos.getBannerPerfil()
                        : null);
        usuario.setCelular((datos.getCelular() != null && !datos.getCelular().isBlank()) ? datos.getCelular() : null);

        // Contraseña opcional
        if (datos.getContrasena() != null && !datos.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(datos.getContrasena()));
        }

        usuarioRepository.save(usuario);
    }

    // ========================
    // GUARDAR USUARIO (GENÉRICO)
    // ========================
    @Transactional
    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    // ========================
    // ASIGNAR ROL ARTISTA
    // ========================
    @Transactional
    public void asignarRolArtista(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rolArtista = rolRepository.findByNombre("ROLE_ARTISTA")
                .orElseThrow(() -> new RuntimeException("El rol ROLE_ARTISTA no existe en la BD"));

        usuario.getRoles().add(rolArtista);
        usuarioRepository.save(usuario);
    }

    // ========================
    // ASIGNAR ROL ADMIN
    // ========================
    @Transactional
    public void asignarRolAdmin(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("El rol ROLE_ADMIN no existe en la BD"));

        usuario.getRoles().add(rolAdmin);
        usuarioRepository.save(usuario);
    }

    // ========================
    // ACTUALIZAR ÚLTIMO LOGIN
    // ========================
    @Transactional
    public void actualizarUltimoLogin(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setUltimoLogin(java.time.LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
}
