/*package com.tectoons.tectoonsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // permite peticiones desde frontend
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ✅ Listar todos los usuarios
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    // ✅ Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Crear nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardar(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    // ✅ Actualizar usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioService.obtenerPorId(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNombreCompleto(usuarioActualizado.getNombreCompleto());
            usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
            usuario.setCorreo(usuarioActualizado.getCorreo());
            usuario.setContrasena(usuarioActualizado.getContrasena());
            usuario.setDescripcion(usuarioActualizado.getDescripcion());
            usuario.setFechaNacimiento(usuarioActualizado.getFechaNacimiento());
            usuario.setCelular(usuarioActualizado.getCelular());
            usuario.setFotoPerfil(usuarioActualizado.getFotoPerfil());
            usuario.setBannerPerfil(usuarioActualizado.getBannerPerfil());
            usuario.setActivo(usuarioActualizado.isActivo());
            return ResponseEntity.ok(usuarioService.guardar(usuario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        if (usuario.isPresent()) {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Buscar por correo
    @GetMapping("/buscar/correo/{correo}")
    public ResponseEntity<Usuario> buscarPorCorreo(@PathVariable String correo) {
        Optional<Usuario> usuario = usuarioService.buscarPorCorreo(correo);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ Buscar por nombre de usuario
    @GetMapping("/buscar/usuario/{nombreUsuario}")
    public ResponseEntity<Usuario> buscarPorNombreUsuario(@PathVariable String nombreUsuario) {
        Optional<Usuario> usuario = usuarioService.buscarPorNombreUsuario(nombreUsuario);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
*/

/*package com.tectoons.tectoonsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/listar"; // templates/usuarios/listar.html
    }

    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/nuevo"; // templates/usuarios/nuevo.html
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        usuarioService.obtenerPorId(id).ifPresent(u -> model.addAttribute("usuario", u));
        return "usuarios/editar"; // templates/usuarios/editar.html
    }

    @PostMapping("/actualizar")
    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios/listar";
    }
}*/
/*package com.tectoons.tectoonsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.tectoons.tectoonsweb.dto.RegistroUsuarioDTO;
import com.tectoons.tectoonsweb.dto.LoginUsuarioDTO;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.model.Rol;
import com.tectoons.tectoonsweb.service.UsuarioService;
import com.tectoons.tectoonsweb.service.RolService;
import com.tectoons.tectoonsweb.service.ApiDniService;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class UsuarioController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private RolService rolService;
    @Autowired private ApiDniService apiDniService;
    @Autowired private PasswordEncoder passwordEncoder;

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registroUsuarioDTO", new RegistroUsuarioDTO());
        return "usuarios/registro";
    }

    // Endpoint AJAX: validar DNI (POST JSON) -> devuelve JSON con nombreCompleto o error
    @PostMapping(value = "/registro/validar-dni", produces = "application/json")
    @ResponseBody
    public Map<String,Object> validarDniAjax(@RequestBody Map<String,String> payload) {
        Map<String,Object> resp = new HashMap<>();
        try {
            String dni = payload.get("dni");
            if (dni == null) throw new Exception("DNI requerido");
            Usuario usuarioApi = apiDniService.obtenerUsuarioPorDni(dni);
            // También verificar si ya está en BD
            Optional<Usuario> existente = usuarioService.buscarPorDni(dni);
            if (existente.isPresent()) {
                resp.put("ok", false);
                resp.put("message", "El DNI ya está registrado como usuario.");
            } else {
                resp.put("ok", true);
                resp.put("nombreCompleto", usuarioApi.getNombreCompleto());
            }
        } catch (Exception e) {
            resp.put("ok", false);
            resp.put("message", e.getMessage());
        }
        return resp;
    }

    // Procesar registro final
    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("registroUsuarioDTO") RegistroUsuarioDTO dto,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            return "usuarios/registro";
        }

        try {
            // Volver a validar DNI con la API (seguridad)
            Usuario usuarioApi = apiDniService.obtenerUsuarioPorDni(dto.getDni());

            // Verificar duplicados en BD
            if (usuarioService.buscarPorDni(dto.getDni()).isPresent()) {
                model.addAttribute("errorMessage", "El DNI ya está registrado.");
                return "usuarios/registro";
            }
            if (usuarioService.buscarPorCorreo(dto.getCorreo()).isPresent()) {
                model.addAttribute("errorMessage", "El correo ya está en uso.");
                return "usuarios/registro";
            }
            if (usuarioService.buscarPorNombreUsuario(dto.getNombreUsuario()).isPresent()) {
                model.addAttribute("errorMessage", "El nombre de usuario ya está en uso.");
                return "usuarios/registro";
            }

            // Crear entidad
            Usuario u = new Usuario();
            u.setDni(dto.getDni());
            // preferimos lo que API arroja (más confiable) pero permitimos dto override si quieres:
            u.setNombreCompleto(usuarioApi.getNombreCompleto());
            u.setNombreUsuario(dto.getNombreUsuario());
            u.setCorreo(dto.getCorreo());
            u.setContrasena(passwordEncoder.encode(dto.getContrasena()));
            if (dto.getFechaNacimiento() != null) {
                u.setFechaNacimiento(java.sql.Date.valueOf(dto.getFechaNacimiento()));
            }

            // Asignar rol USUARIO (crear si no existe)
            Rol rolUsuario = rolService.buscarPorNombre("USUARIO");
            if (rolUsuario == null) {
                rolUsuario = new Rol();
                rolUsuario.setNombre("USUARIO");
                rolUsuario = rolService.guardar(rolUsuario);
            }
            u.getRoles().add(rolUsuario);

            usuarioService.guardar(u);

            model.addAttribute("mensaje", "Usuario registrado correctamente. Por favor inicia sesión.");
            model.addAttribute("loginUsuarioDTO", new LoginUsuarioDTO());
            return "usuarios/login";

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "usuarios/registro";
        }
    }

    // Mostrar login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("loginUsuarioDTO", new LoginUsuarioDTO());
        return "usuarios/login";
    }

    // Procesar login
    @PostMapping("/login")
    public String loginUsuario(@Valid @ModelAttribute("loginUsuarioDTO") LoginUsuarioDTO dto,
                               BindingResult result,
                               Model model,
                               HttpSession session) {
        if (result.hasErrors()) {
            return "usuarios/login";
        }

        Optional<Usuario> usuarioOpt = usuarioService.buscarPorDni(dto.getUsername());
        if (usuarioOpt.isEmpty()) usuarioOpt = usuarioService.buscarPorCorreo(dto.getUsername());
        if (usuarioOpt.isEmpty()) usuarioOpt = usuarioService.buscarPorNombreUsuario(dto.getUsername());

        if (usuarioOpt.isPresent() && passwordEncoder.matches(dto.getContrasena(), usuarioOpt.get().getContrasena())) {
            session.setAttribute("usuarioId", usuarioOpt.get().getId());
            // puedes guardar nombreUsuario también
            session.setAttribute("usuarioNombre", usuarioOpt.get().getNombreUsuario());
            return "redirect:/home";
        }

        model.addAttribute("errorMessage", "Usuario o contraseña incorrectos.");
        return "usuarios/login";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        Long id = (Long) session.getAttribute("usuarioId");
        if (id != null) {
            usuarioService.obtenerPorId(id).ifPresent(u -> model.addAttribute("usuario", u));
        }
        return "usuarios/home";
    }

    // Logout simple
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
*/

/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute Usuario usuario,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registro";
        }

        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "El correo ya está registrado");
            return "registro";
        }

        if (usuarioService.existeNombreUsuario(usuario.getNombreUsuario())) {
            model.addAttribute("error", "El nombre de usuario ya está en uso");
            return "registro";
        }

        usuarioService.registrarUsuario(usuario);
        model.addAttribute("exito", "Usuario registrado con éxito. Inicia sesión.");
        return "login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUsuario(@RequestParam String identificador,
                               @RequestParam String contrasena,
                               HttpSession session, Model model) {
        Optional<Usuario> usuario = usuarioService.login(identificador, contrasena);

        if (usuario.isPresent()) {
            session.setAttribute("usuarioActual", usuario.get());
            return "redirect:/"; // Redirige al home
        }

        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario/login";
    }
}
*/

/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute Usuario usuario,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registro";
        }

        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "El correo ya está registrado");
            return "registro";
        }

        if (usuarioService.existeNombreUsuario(usuario.getNombreUsuario())) {
            model.addAttribute("error", "El nombre de usuario ya está en uso");
            return "registro";
        }

        usuarioService.registrarUsuario(usuario);
        model.addAttribute("exito", "Usuario registrado con éxito. Inicia sesión.");
        return "login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUsuario(@RequestParam String identificador,
                               @RequestParam String contrasena,
                               HttpSession session, Model model) {
        Optional<Usuario> usuario = usuarioService.login(identificador, contrasena);

        if (usuario.isPresent()) {
            session.setAttribute("usuarioActual", usuario.get());
            return "redirect:/";
        }

        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario/login";
    }
}*/

/* 
package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            return "registro";
        }

        if (usuarioService.existePorCorreo(usuario.getCorreo()) ||
                usuarioService.existePorNombreUsuario(usuario.getNombreUsuario())) {
            model.addAttribute("error", "El usuario o correo ya está registrado.");
            return "registro";
        }

        usuarioService.registrar(usuario);
        model.addAttribute("exito", "Usuario registrado exitosamente.");
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String identificador,
                                @RequestParam String contrasena,
                                Model model) {

        Usuario usuario = usuarioService.iniciarSesion(identificador, contrasena);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            return "redirect:/inicio"; // puedes cambiarlo por otra vista
        } else {
            model.addAttribute("error", "Credenciales incorrectas.");
            return "login";
        }
    }
}
*/

/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 🧩 Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // 🧩 Procesar registro
    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            System.out.println("❌ Error de validación en registro:");
            result.getAllErrors().forEach(e -> System.out.println(" - " + e.getDefaultMessage()));
            return "registro";
        }

        if (usuarioService.existePorCorreo(usuario.getCorreo()) ||
            usuarioService.existePorNombreUsuario(usuario.getNombreUsuario())) {
            model.addAttribute("error", "El usuario o correo ya está registrado.");
            return "registro";
        }

        usuarioService.registrar(usuario);
        model.addAttribute("exito", "Usuario registrado exitosamente.");
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // 🧩 Mostrar formulario de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // 🧩 Procesar login y guardar usuario en sesión
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String identificador,
                                @RequestParam String contrasena,
                                HttpSession session,
                                Model model) {

        var usuario = usuarioService.iniciarSesion(identificador, contrasena);
        if (usuario != null) {
            // ✅ Guardar usuario en sesión
            session.setAttribute("usuarioActual", usuario);

            System.out.println("✅ Usuario en sesión: " + usuario.getNombreUsuario());

            return "redirect:/navbar-test"; // Redirige al inicio
        } else {
            // ⚠️ Enviar de nuevo el valor ingresado para mantenerlo en el formulario
            model.addAttribute("error", "Credenciales incorrectas.");
            model.addAttribute("identificador", identificador);
            return "login";
        }
    }

    // 🧩 Cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Elimina la sesión
        return "redirect:/navbar-test";
    }
}
*/

/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // === FORMULARIO DE REGISTRO ===
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor completa todos los campos correctamente.");
            return "registro";
        }

        if (usuarioService.existePorCorreo(usuario.getCorreo()) ||
            usuarioService.existePorNombreUsuario(usuario.getNombreUsuario()) || usuarioService.existePorDni(usuario.getDni())) {
            model.addAttribute("error", "El DNI, usuario o correo ya está registrado.");
            return "registro";
        }

        usuarioService.registrar(usuario);
        model.addAttribute("exito", "Usuario registrado exitosamente.");
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // === LOGIN ===
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("identificador", "");
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String identificador,
                                @RequestParam String contrasena,
                                HttpSession session,
                                Model model) {

        var usuario = usuarioService.iniciarSesion(identificador, contrasena);
        if (usuario != null) {
            session.setAttribute("usuarioActual", usuario);
            return "redirect:/navbar-test";
        }

        model.addAttribute("error", "Credenciales incorrectas.");
        model.addAttribute("identificador", identificador);
        return "login";
    }

    // === LOGOUT ===
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/navbar-test";
    }
}*/

/*
package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // === FORMULARIO DE REGISTRO ===
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor completa todos los campos correctamente.");
            return "registro";
        }

        try {
            usuarioService.registrar(usuario);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }

        model.addAttribute("exito", "Usuario registrado exitosamente.");
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // === LOGIN ===
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String error,
                               Model model) {

        model.addAttribute("identificador", "");

        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas.");
        }

        return "login";
    }

    // === LOGOUT (Spring Security lo maneja, solo redirige) ===
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/navbar-test";
    }
}*/

/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tectoons.tectoonsweb.security.CustomUserDetails;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ========================
    // REGISTRO DE USUARIO
    // ========================
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor completa todos los campos correctamente.");
            return "registro";
        }

        try {
            usuarioService.registrar(usuario);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }

        model.addAttribute("exito", "Usuario registrado exitosamente.");
        model.addAttribute("usuario", new Usuario());
        return "usuario/registro";
    }

    // ========================
    // LOGIN
    // ========================
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String error,
                               Model model) {

        model.addAttribute("identificador", "");

        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas.");
        }

        return "usuario/login";
    }

    // ========================
    // PERFIL DE USUARIO
    // ========================
    @GetMapping("/perfil")
    public String mostrarPerfil(@AuthenticationPrincipal CustomUserDetails usuarioDetails,
                                Model model) {

        Usuario usuario = usuarioService.buscarPorId(usuarioDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        return "usuario/perfil";
    }

    // ========================
    // FORMULARIO DE EDICIÓN DE PERFIL
    // ========================
    @GetMapping("/editar-perfil")
    public String mostrarEditarPerfil(@AuthenticationPrincipal CustomUserDetails usuarioDetails,
                                      Model model) {

        Usuario usuario = usuarioService.buscarPorId(usuarioDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        return "usuario/editar-perfil";
    }

    @PostMapping("/editar-perfil")
    public String editarPerfil(@AuthenticationPrincipal CustomUserDetails usuarioDetails,
                               @Valid @ModelAttribute("usuario") Usuario usuario,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor corrige los errores del formulario.");
            return "usuario/editar-perfil";
        }

        try {
            usuarioService.actualizarPerfil(usuarioDetails.getId(), usuario);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "usuario/editar-perfil";
        }

        model.addAttribute("exito", "Perfil actualizado correctamente.");
        return "usuario/editar-perfil";
    }

    // ========================
    // LOGOUT
    // ========================
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/obras/portafolio";
    }
}*/

package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.UsuarioService;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ========================
    // REGISTRO DE USUARIO
    // ========================
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult result,
            Model model) {
        // borrar si pasa algo
        // Validar que la contraseña no esté vacía
        // if (usuario.getContrasena() == null || usuario.getContrasena().isBlank()) {
        // model.addAttribute("error", "La contraseña es obligatoria");
        // return "usuario/registro";
        // }
        // hasta aqui

        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor completa todos los campos correctamente.");
            return "usuario/registro";
        }

        try {
            usuarioService.registrar(usuario);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "usuario/registro";
        }

        model.addAttribute("exito", "Usuario registrado exitosamente.");
        model.addAttribute("usuario", new Usuario());
        return "usuario/registro";
    }

    // ========================
    // LOGIN
    // ========================
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String error,
            Model model) {

        model.addAttribute("identificador", "");

        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas.");
        }

        return "usuario/login";
    }

    // ========================
    // PERFIL DE USUARIO
    // ========================
    @GetMapping("/perfil")
    public String mostrarPerfil(@AuthenticationPrincipal CustomUserDetails usuarioDetails,
            Model model) {

        Usuario usuario = usuarioService.buscarPorId(usuarioDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        return "usuario/perfil";
    }

    // ========================
    // FORMULARIO DE EDICIÓN DE PERFIL
    // ========================
    @GetMapping("/editar-perfil")
    public String mostrarEditarPerfil(@AuthenticationPrincipal CustomUserDetails usuarioDetails,
            Model model) {

        Usuario usuario = usuarioService.buscarPorId(usuarioDetails.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        return "usuario/editar-perfil";
    }

    @PostMapping("/editar-perfil")
    public String editarPerfil(@AuthenticationPrincipal CustomUserDetails usuarioDetails,
            @ModelAttribute("usuario") Usuario usuario,
            BindingResult result,
            @RequestParam(name = "repetirContrasena", required = false) String repetirContrasena,
            Model model) {

        // Validación básica de formulario
        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor corrige los errores del formulario.");
            return "usuario/editar-perfil";
        }

        // Validación de contraseña opcional
        if (usuario.getContrasena() != null && !usuario.getContrasena().isBlank()) {
            if (!usuario.getContrasena().equals(repetirContrasena)) {
                model.addAttribute("error", "Las contraseñas no coinciden.");
                return "usuario/editar-perfil";
            }
            if (usuario.getContrasena().length() < 8) {
                model.addAttribute("error", "La contraseña debe tener al menos 8 caracteres");
                return "usuario/editar-perfil";
            }
        }

        // Validación de celular opcional
        if (usuario.getCelular() != null && !usuario.getCelular().isBlank()) {
            if (!usuario.getCelular().matches("\\d{9}")) {
                model.addAttribute("error", "El número de celular debe tener 9 dígitos.");
                return "usuario/editar-perfil";
            }
        }

        try {
            usuarioService.actualizarPerfil(usuarioDetails.getId(), usuario);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "usuario/editar-perfil";
        }

        model.addAttribute("exito", "Perfil actualizado correctamente.");
        return "usuario/editar-perfil";
    }

    // ========================
    // LOGOUT
    // ========================
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/portafolio-obras";
    }
}
