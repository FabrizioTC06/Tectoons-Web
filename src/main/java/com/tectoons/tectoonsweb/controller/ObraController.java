/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/obras")
@CrossOrigin(origins = "*")
public class ObraRestController {

    @Autowired
    private ObraService obraService;

    @GetMapping
    public List<Obra> listarTodos() {
        return obraService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obra> obtenerPorId(@PathVariable Integer id) {
        Optional<Obra> obra = obraService.obtenerPorId(id);
        return obra.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Obra> crear(@RequestBody Obra obra) {
        return ResponseEntity.ok(obraService.guardar(obra));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Obra> actualizar(@PathVariable Integer id, @RequestBody Obra obraActualizada) {
        Optional<Obra> obraExistente = obraService.obtenerPorId(id);
        if (obraExistente.isPresent()) {
            Obra obra = obraExistente.get();
            obra.setTitulo(obraActualizada.getTitulo());
            obra.setDescripcion(obraActualizada.getDescripcion());
            obra.setImagenUrl(obraActualizada.getImagenUrl());
            obra.setVideoUrl(obraActualizada.getVideoUrl());
            return ResponseEntity.ok(obraService.guardar(obra));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Obra> obra = obraService.obtenerPorId(id);
        if (obra.isPresent()) {
            obraService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/artista/{artistaId}")
    public List<Obra> buscarPorArtista(@PathVariable Integer artistaId) {
        return obraService.buscarPorArtista(artistaId);
    }
}
*/

/*package com.tectoons.tectoonsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.ObraService;
import com.tectoons.tectoonsweb.service.LikeService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @Autowired
    private LikeService likeService;

    // === CRUD BÁSICO ===

    @GetMapping
    public String listarObras(Model model) {
        List<Obra> obras = obraService.listarTodos();
        model.addAttribute("obras", obras);
        return "obras/listar";
    }

    @GetMapping("/nuevo")
    public String nuevaObra(Model model) {
        model.addAttribute("obra", new Obra());
        return "obras/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarObra(@ModelAttribute Obra obra) {
        obraService.guardar(obra);
        return "redirect:/obras";
    }

    @GetMapping("/editar/{id}")
    public String editarObra(@PathVariable Long id, Model model) {
        obraService.obtenerPorId(id).ifPresent(o -> model.addAttribute("obra", o));
        return "obras/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarObra(@ModelAttribute Obra obra) {
        obraService.guardar(obra);
        return "redirect:/obras";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarObra(@PathVariable Long id) {
        obraService.eliminar(id);
        return "redirect:/obras";
    }

    // === PORTAFOLIO CON FILTROS Y ORDENAMIENTO ===

    @GetMapping("/portafolio")
public String verPortafolio(
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) String busqueda,
        @RequestParam(required = false, defaultValue = "tituloAsc") String orden,
        Model model,
        @SessionAttribute(name = "usuarioActual", required = false) Usuario usuario // Opcional
) {
    List<Obra> obras = obraService.buscarFiltrarOrdenar(busqueda, categoria, orden);
    List<String> categorias = List.of("Ilustración", "Personajes", "Animación", "Estilizado", "Escenarios");
if ("favoritos".equals(orden) && usuario != null) {
    Set<Long> obrasConLike = likeService.obtenerIdsObrasConLike(usuario.getId());

    obras.sort((o1, o2) -> {
        boolean o1Like = obrasConLike.contains(o1.getId());
        boolean o2Like = obrasConLike.contains(o2.getId());
        return Boolean.compare(o2Like, o1Like); // true primero
    });
}



    model.addAttribute("obras", obras);
    model.addAttribute("categorias", categorias);
    model.addAttribute("categoria", categoria != null ? categoria : "all");
    model.addAttribute("busqueda", busqueda != null ? busqueda : "");
    model.addAttribute("orden", orden);
    
    // Pasar ID de usuario al JS (si está logueado)
    model.addAttribute("usuarioId", usuario != null ? usuario.getId() : null);

    return "portafolio";
}

}
*/

/*
package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.LikeService;
import com.tectoons.tectoonsweb.service.ObraService;
import com.tectoons.tectoonsweb.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UsuarioService usuarioService;

    // === CRUD ===
    @GetMapping
    public String listarObras(Model model) {
        
        model.addAttribute("obras", obraService.listarTodos());
        return "obras/listar";
    }

    @GetMapping("/nuevo")
    public String nuevaObra(Model model) {
        model.addAttribute("obra", new Obra());
        return "obras/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarObra(@Valid @ModelAttribute("obra") Obra obra,
                              BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", "Completa todos los campos correctamente.");
            return "obras/nuevo";
        }

        obraService.guardar(obra);
        return "redirect:/obras";
    }

    @GetMapping("/editar/{id}")
    public String editarObra(@PathVariable Long id, Model model) {
        obraService.obtenerPorId(id).ifPresent(o -> model.addAttribute("obra", o));
        return "obras/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarObra(@Valid @ModelAttribute("obra") Obra obra,
                                 BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", "Corrige los errores del formulario.");
            return "obras/editar";
        }

        obraService.guardar(obra);
        return "redirect:/obras";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarObra(@PathVariable Long id) {
        obraService.eliminar(id);
        return "redirect:/obras";
    }

    // === PORTAFOLIO ===
    @GetMapping("/portafolio")
    public String verPortafolio(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false, defaultValue = "tituloAsc") String orden,
            Model model) {

        // Obtener usuario logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long usuarioId = null;
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {

            Usuario usuario = usuarioService.buscarPorIdentificador(auth.getName()); // correo o username
            if (usuario != null) {
                usuarioId = usuario.getId();
            }
        }

        // Obtener obras filtradas
        List<Obra> obras = obraService.buscarFiltrarOrdenar(busqueda, categoria, orden);

        // ➤ AGREGAR NUMERO DE LIKES A CADA OBRA (LÍNEA QUE PEDÍAS)
    obras.forEach(obra ->
            obra.setTotalLikes(likeService.countLikes(obra.getId()))
    );

        // Filtros de favoritos
        if ("favoritos".equals(orden) && usuarioId != null) {
            Set<Long> obrasConLike = likeService.obtenerIdsObrasConLike(usuarioId);

            obras.sort((o1, o2) -> Boolean.compare(
                    obrasConLike.contains(o2.getId()),
                    obrasConLike.contains(o1.getId())
            ));
        }

        // Enviar variables a la vista
        model.addAttribute("obras", obras);
        model.addAttribute("categoria", categoria != null ? categoria : "all");
        model.addAttribute("busqueda", busqueda != null ? busqueda : "");
        model.addAttribute("orden", orden);
        model.addAttribute("usuarioId", usuarioId);

        // Lista estática de categorías
        model.addAttribute("categorias", List.of("Ilustración", "Personajes", "Animación", "Estilizado", "Escenarios"));

        return "portafolio";
    }
}*/
/*
//CON CRUD PARA ARTISTAS Y PORTAFOLIO PUBLICO
package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.LikeService;
import com.tectoons.tectoonsweb.service.ObraService;
//import com.tectoons.tectoonsweb.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @Autowired
    private LikeService likeService;

    //@Autowired
    //private UsuarioService usuarioService;

    // ===========================================================
    // 🔐 OBTENER USUARIO LOGUEADO
    // ===========================================================
    private Usuario getUsuarioLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }

        CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
        return cud.getUsuario();
    }

    private Artista getArtistaLogueado() {
        Usuario u = getUsuarioLogueado();
        return (u != null ? u.getArtista() : null);
    }

    // ===========================================================
    // 👨‍🎨 CRUD SOLO PARA ARTISTAS
    // ===========================================================

    @GetMapping("/mis-obras")
    public String listarObrasArtista(Model model) {
        Artista artista = getArtistaLogueado();

        if (artista == null) {
            return "redirect:/usuario/login";
        }

        model.addAttribute("obras", obraService.buscarPorArtista(artista.getId()));
        return "obras/artist-list";
    }

    @GetMapping("/nuevo")
    public String nuevaObra(Model model) {
        Artista artista = getArtistaLogueado();

        if (artista == null) {
            return "redirect:/usuario/login";
        }

        Obra obra = new Obra();
        obra.setArtista(artista); // Preasignar artista

        model.addAttribute("obra", obra);
        return "obras/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarObra(@Valid @ModelAttribute("obra") Obra obra,
                              BindingResult result,
                              Model model) {

        Artista artista = getArtistaLogueado();

        if (artista == null) {
            return "redirect:/usuario/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("error", "Completa todos los campos correctamente.");
            return "obras/nuevo";
        }

        // Asignar artista al guardar
        obra.setArtista(artista);

        obraService.guardar(obra);
        return "redirect:/obras/mis-obras";
    }

    @GetMapping("/editar/{id}")
    public String editarObra(@PathVariable Long id, Model model) {
        Artista artista = getArtistaLogueado();
        if (artista == null) return "redirect:/usuario/login";

        Obra obra = obraService.obtenerPorId(id).orElse(null);
        if (obra == null || obra.getArtista().getId() != artista.getId()) {
            return "redirect:/obras/mis-obras"; // Evita editar obras ajenas
        }

        model.addAttribute("obra", obra);
        return "obras/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarObra(@Valid @ModelAttribute("obra") Obra obra,
                                 BindingResult result,
                                 Model model) {

        Artista artista = getArtistaLogueado();
        if (artista == null) return "redirect:/usuario/login";

        if (result.hasErrors()) {
            model.addAttribute("error", "Corrige los errores del formulario.");
            return "obras/editar";
        }

        // Bloquear cambios si la obra no pertenece al artista
        Obra original = obraService.obtenerPorId(obra.getId()).orElse(null);

        if (original == null || original.getArtista().getId() != artista.getId()) {
            return "redirect:/obras/mis-obras";
        }

        obra.setArtista(artista); // asegurar la propiedad

        obraService.guardar(obra);
        return "redirect:/obras/mis-obras";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarObra(@PathVariable Long id) {
        Artista artista = getArtistaLogueado();
        if (artista == null) return "redirect:/usuario/login";

        Obra obra = obraService.obtenerPorId(id).orElse(null);
        if (obra != null && obra.getArtista().getId() == artista.getId()) {
            obraService.eliminar(id);
        }

        return "redirect:/obras/mis-obras";
    }

    // ===========================================================
    // 🎨 PORTAFOLIO PÚBLICO
    // ===========================================================
    @GetMapping("/portafolio")
    public String verPortafolio(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false, defaultValue = "tituloAsc") String orden,
            Model model) {

        Usuario usuario = getUsuarioLogueado();
        Long usuarioId = (usuario != null ? usuario.getId() : null);

        List<Obra> obras = obraService.buscarFiltrarOrdenar(busqueda, categoria, orden);

        obras.forEach(o -> o.setTotalLikes(likeService.countLikes(o.getId())));

        if ("favoritos".equals(orden) && usuarioId != null) {
            Set<Long> likes = likeService.obtenerIdsObrasConLike(usuarioId);

            obras.sort((a, b) -> Boolean.compare(
                    likes.contains(b.getId()),
                    likes.contains(a.getId())
            ));
        }

        model.addAttribute("obras", obras);
        model.addAttribute("categoria", categoria != null ? categoria : "all");
        model.addAttribute("busqueda", busqueda != null ? busqueda : "");
        model.addAttribute("orden", orden);
        model.addAttribute("usuarioId", usuarioId);

        model.addAttribute("categorias", List.of(
                "Ilustración", "Personajes", "Animación", "Estilizado", "Escenarios"
        ));

        return "portafolio";
    }
}
*/

//final controlador obra

// CON CRUD PARA ARTISTAS Y PORTAFOLIO PUBLICO
package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Artista;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.security.CustomUserDetails;
import com.tectoons.tectoonsweb.service.LikeService;
import com.tectoons.tectoonsweb.service.ObraService;

//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class ObraController {

    @Autowired
    private ObraService obraService;

    @Autowired
    private LikeService likeService;

    // ===========================================================
    // 🔐 OBTENER USUARIO LOGUEADO
    // ===========================================================
    private Usuario getUsuarioLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }

        return ((CustomUserDetails) auth.getPrincipal()).getUsuario();
    }

    private Artista getArtistaLogueado() {
        Usuario u = getUsuarioLogueado();
        return (u != null ? u.getArtista() : null);
    }

    // ===========================================================
    // 👨‍🎨 CRUD PRIVADO SOLO PARA ARTISTAS
    // ===========================================================

    @GetMapping("/artista/mis-obras")
public String panelObrasArtista(Model model) {
    Artista artista = getArtistaLogueado();
    if (artista == null) return "redirect:/usuario/login";

    model.addAttribute("artistaId", artista.getId());
    return "artista/obras/panel"; // Aquí irá tu CRUD con AJAX
}

    /*@GetMapping("/mis-obras")
    public String listarObrasArtista(Model model) {
        Artista artista = getArtistaLogueado();

        if (artista == null) {
            return "redirect:/usuario/login";
        }

        model.addAttribute("obras", obraService.buscarPorArtista(artista.getId()));
        return "obras/artista/artist-list";
    }*/
/*
    @GetMapping("/nuevo")
    public String nuevaObra(Model model) {
        Artista artista = getArtistaLogueado();
        if (artista == null) return "redirect:/usuario/login";

        Obra obra = new Obra();
        obra.setArtista(artista);

        model.addAttribute("obra", obra);
        return "obras/artista/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarObra(@Valid @ModelAttribute("obra") Obra obra,
                              BindingResult result,
                              Model model) {

        Artista artista = getArtistaLogueado();
        if (artista == null) return "redirect:/usuario/login";

        if (result.hasErrors()) {
            model.addAttribute("error", "Completa todos los campos correctamente.");
            return "obras/artista/nuevo";
        }

        if (obra.getVideoUrl() != null && obra.getVideoUrl().isBlank()) {
    obra.setVideoUrl(null);
}

        obra.setArtista(artista);
        obraService.guardar(obra);

        return "redirect:/obras/mis-obras";
    }

    @GetMapping("/editar/{id}")
    public String editarObra(@PathVariable Long id, Model model) {
        Artista artista = getArtistaLogueado();
        if (artista == null) return "redirect:/usuario/login";

        Obra obra = obraService.obtenerPorId(id).orElse(null);

        if (obra == null || !obra.getArtista().getId().equals(artista.getId())) {
            return "redirect:/obras/mis-obras";
        }

        model.addAttribute("obra", obra);
        return "obras/artista/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarObra(@Valid @ModelAttribute("obra") Obra obra,
                                 BindingResult result,
                                 Model model) {

        Artista artista = getArtistaLogueado();
        if (artista == null) return "redirect:/usuario/login";

        if (result.hasErrors()) {
            model.addAttribute("error", "Corrige los errores del formulario.");
            return "obras/artista/editar";
        }

        // Evitar cambios de obras ajenas
        Obra original = obraService.obtenerPorId(obra.getId()).orElse(null);
        if (original == null || !original.getArtista().getId().equals(artista.getId())) {
            return "redirect:/obras/mis-obras";
        }
if (obra.getVideoUrl() != null && obra.getVideoUrl().isBlank()) {
    obra.setVideoUrl(null);
}
        obra.setArtista(artista);
        obraService.guardar(obra);

        return "redirect:/obras/mis-obras";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarObra(@PathVariable Long id) {
        Artista artista = getArtistaLogueado();
        if (artista == null) return "redirect:/usuario/login";

        Obra obra = obraService.obtenerPorId(id).orElse(null);

        if (obra != null && obra.getArtista().getId().equals(artista.getId())) {
            obraService.eliminar(id);
        }

        return "redirect:/obras/mis-obras";
    }
*/
    // ===========================================================
    // 🎨 PORTAFOLIO PÚBLICO
    // ===========================================================
    @GetMapping("/portafolio-obras")
    public String verPortafolio(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false, defaultValue = "tituloAsc") String orden,
            Model model) {

        Usuario usuario = getUsuarioLogueado();
        Long usuarioId = (usuario != null ? usuario.getId() : null);

        List<Obra> obras = obraService.buscarFiltrarOrdenar(busqueda, categoria, orden);

        // Añadir likes
        obras.forEach(o -> o.setTotalLikes(likeService.countLikes(o.getId())));

        // Ordenar favoritos si aplica
        if ("favoritos".equals(orden) && usuarioId != null) {
            Set<Long> likes = likeService.obtenerIdsObrasConLike(usuarioId);

            obras.sort((a, b) -> Boolean.compare(
                    likes.contains(b.getId()),
                    likes.contains(a.getId())
            ));
        }

        model.addAttribute("obras", obras);
        model.addAttribute("categoria", categoria != null ? categoria : "all");
        model.addAttribute("busqueda", busqueda != null ? busqueda : "");
        model.addAttribute("orden", orden);
        model.addAttribute("usuarioId", usuarioId);

        model.addAttribute("categorias", List.of(
                "Ilustración", "Personajes", "Animación", "Estilizado", "Escenarios"
        ));

        return "portafolio-obras";
    }
}


/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.CategoriaService;
import com.tectoons.tectoonsweb.service.LikeService;
import com.tectoons.tectoonsweb.service.ObraService;
import com.tectoons.tectoonsweb.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UsuarioService usuarioService;


    // ==================================================
    // LISTAR
    // ==================================================
    @GetMapping
    public String listarObras(Model model) {
        model.addAttribute("obras", obraService.listarTodos());
        return "obras/listar";
    }


    // ==================================================
    // NUEVA OBRA
    // ==================================================
    @GetMapping("/nuevo")
    public String nuevaObra(Model model) {
        model.addAttribute("obra", new Obra());
        model.addAttribute("categorias", categoriaService.listar());
        return "obras/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarObra(
            @Valid @ModelAttribute("obra") Obra obra,
            @RequestParam(value = "categoriaIds", required = false) List<Long> categoriaIds,
            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listar());
            model.addAttribute("error", "Completa todos los campos correctamente.");
            return "obras/nuevo";
        }

        obraService.guardarConCategorias(obra, categoriaIds);
        return "redirect:/obras";
    }


    // ==================================================
    // EDITAR OBRA
    // ==================================================
    @GetMapping("/editar/{id}")
    public String editarObra(@PathVariable Long id, Model model) {

        Optional<Obra> obraOpt = obraService.obtenerPorId(id);

        if (obraOpt.isEmpty()) {
            return "redirect:/obras?error=notfound";
        }

        model.addAttribute("obra", obraOpt.get());
        model.addAttribute("categorias", categoriaService.listar());

        return "obras/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarObra(
            @Valid @ModelAttribute("obra") Obra obra,
            @RequestParam(value = "categoriaIds", required = false) List<Long> categoriaIds,
            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listar());
            model.addAttribute("error", "Corrige los errores.");
            return "obras/editar";
        }

        obraService.guardarConCategorias(obra, categoriaIds);
        return "redirect:/obras";
    }


    // ==================================================
    // ELIMINAR
    // ==================================================
    @GetMapping("/eliminar/{id}")
    public String eliminarObra(@PathVariable Long id) {
        obraService.eliminar(id);
        return "redirect:/obras";
    }


    // ==================================================
    // PORTAFOLIO + FILTROS
    // ==================================================
    @GetMapping("/portafolio")
    public String verPortafolio(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false, defaultValue = "tituloAsc") String orden,
            Model model
    ) {

        // =============================
        // Obtener usuario logueado
        // =============================
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long usuarioId = null;

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            Usuario usuario = usuarioService.buscarPorIdentificador(auth.getName());
            if (usuario != null) usuarioId = usuario.getId();
        }

        // =============================
        // Obtener obras con filtros
        // =============================
        List<Obra> obras = obraService.buscarFiltrarOrdenar(busqueda, categoria, orden);

        obras.forEach(obra ->
                obra.setTotalLikes(likeService.countLikes(obra.getId()))
        );

        // =============================
        // Ordenar por favoritos
        // =============================
        if ("favoritos".equals(orden) && usuarioId != null) {

            Set<Long> obrasConLike = likeService.obtenerIdsObrasConLike(usuarioId);

            obras.sort((o1, o2) ->
                    Boolean.compare(
                            obrasConLike.contains(o2.getId()),
                            obrasConLike.contains(o1.getId())
                    )
            );
        }

        // =============================
        // Enviar datos a la vista
        // =============================
        model.addAttribute("obras", obras);
        model.addAttribute("categoria", categoria != null ? categoria : "all");
        model.addAttribute("busqueda", busqueda != null ? busqueda : "");
        model.addAttribute("orden", orden);
        model.addAttribute("usuarioId", usuarioId);

        model.addAttribute("categorias", categoriaService.listar());

        return "portafolio";
    }
}*/




/* previo con http session
package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.LikeService;
import com.tectoons.tectoonsweb.service.ObraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @Autowired
    private LikeService likeService;

    // === CRUD ===
    @GetMapping
    public String listarObras(Model model) {
        model.addAttribute("obras", obraService.listarTodos());
        return "obras/listar";
    }

    @GetMapping("/nuevo")
    public String nuevaObra(Model model) {
        model.addAttribute("obra", new Obra());
        return "obras/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarObra(@Valid @ModelAttribute("obra") Obra obra,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Completa todos los campos correctamente.");
            return "obras/nuevo";
        }
        obraService.guardar(obra);
        return "redirect:/obras";
    }

    @GetMapping("/editar/{id}")
    public String editarObra(@PathVariable Long id, Model model) {
        obraService.obtenerPorId(id).ifPresent(o -> model.addAttribute("obra", o));
        return "obras/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarObra(@Valid @ModelAttribute("obra") Obra obra,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Corrige los errores del formulario.");
            return "obras/editar";
        }
        obraService.guardar(obra);
        return "redirect:/obras";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarObra(@PathVariable Long id) {
        obraService.eliminar(id);
        return "redirect:/obras";
    }

    // === PORTAFOLIO CON FILTROS Y ORDEN ===
    @GetMapping("/portafolio")
    public String verPortafolio(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false, defaultValue = "tituloAsc") String orden,
            @SessionAttribute(name = "usuarioActual", required = false) Usuario usuario,
            Model model) {

        List<Obra> obras = obraService.buscarFiltrarOrdenar(busqueda, categoria, orden);
        List<String> categorias = List.of("Ilustración", "Personajes", "Animación", "Estilizado", "Escenarios");

        if ("favoritos".equals(orden) && usuario != null) {
            Set<Long> obrasConLike = likeService.obtenerIdsObrasConLike(usuario.getId());
            obras.sort((o1, o2) -> Boolean.compare(
                    obrasConLike.contains(o2.getId()),
                    obrasConLike.contains(o1.getId())
            ));
        }

        model.addAttribute("obras", obras);
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoria", categoria != null ? categoria : "all");
        model.addAttribute("busqueda", busqueda != null ? busqueda : "");
        model.addAttribute("orden", orden);
        model.addAttribute("usuarioId", usuario != null ? usuario.getId() : null);

        return "portafolio";
    }
}
*/

/*@GetMapping("/portafolio")
    public String verPortafolio(
            @RequestParam(value = "categoria", required = false, defaultValue = "all") String categoria,
            @RequestParam(value = "busqueda", required = false, defaultValue = "") String busqueda,
            @RequestParam(value = "orden", required = false, defaultValue = "fechaDesc") String orden,
            Model model) {

        List<String> categorias = List.of("Ilustración", "Personajes", "Animación", "Estilizado", "Escenarios");
        List<Obra> obras = obraService.filtrarBuscarOrdenar(categoria, busqueda, orden);

        model.addAttribute("obras", obras);
        model.addAttribute("categorias", categorias);
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("orden", orden);

        return "portafolio";
    }*/

   /*package com.tectoons.tectoonsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tectoons.tectoonsweb.model.Obra;
import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.service.ObraService;
import com.tectoons.tectoonsweb.service.LikeService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @Autowired
    private LikeService likeService;

    // === CRUD BÁSICO ===

    @GetMapping
    public String listarObras(Model model) {
        List<Obra> obras = obraService.listarTodos();
        model.addAttribute("obras", obras);
        return "obras/listar";
    }

    @GetMapping("/nuevo")
    public String nuevaObra(Model model) {
        model.addAttribute("obra", new Obra());
        return "obras/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarObra(@ModelAttribute Obra obra) {
        obraService.guardar(obra);
        return "redirect:/obras";
    }

    @GetMapping("/editar/{id}")
    public String editarObra(@PathVariable Integer id, Model model) {
        obraService.obtenerPorId(id).ifPresent(o -> model.addAttribute("obra", o));
        return "obras/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarObra(@ModelAttribute Obra obra) {
        obraService.guardar(obra);
        return "redirect:/obras";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarObra(@PathVariable Integer id) {
        obraService.eliminar(id);
        return "redirect:/obras";
    }

    // === PORTAFOLIO CON FILTROS, ORDENAMIENTO Y LIKES ===

    @GetMapping("/portafolio")
    public String verPortafolio(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false, defaultValue = "tituloAsc") String orden,
            Model model,
            @SessionAttribute("usuario") Usuario usuario // Obtener usuario logueado
    ) {
        // Obtener lista filtrada y ordenada
        List<Obra> obras = obraService.buscarFiltrarOrdenar(busqueda, categoria, orden);

        // Lista de categorías disponibles
        List<String> categorias = List.of("Ilustración", "Personajes", "Animación", "Estilizado", "Escenarios");

        // Obtener IDs de obras que el usuario ya le dio like
        Set<Long> obrasConLike = likeService.obtenerIdsObrasConLike(usuario.getId());

        // Pasar atributos a Thymeleaf
        model.addAttribute("obras", obras);
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoria", categoria != null ? categoria : "all");
        model.addAttribute("busqueda", busqueda != null ? busqueda : "");
        model.addAttribute("orden", orden);
        model.addAttribute("usuarioId", usuario.getId());
        model.addAttribute("obrasConLike", obrasConLike);

        return "portafolio";
    }
}
*/