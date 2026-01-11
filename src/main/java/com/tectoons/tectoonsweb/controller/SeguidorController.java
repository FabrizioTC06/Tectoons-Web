/*package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Seguidor;
import com.tectoons.tectoonsweb.service.SeguidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seguidores")
@CrossOrigin(origins = "*")
public class SeguidorController {

    @Autowired
    private SeguidorService seguidorService;

    @GetMapping
    public List<Seguidor> listarTodos() {
        return seguidorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguidor> obtenerPorId(@PathVariable Integer id) {
        Optional<Seguidor> seguidor = seguidorService.obtenerPorId(id);
        return seguidor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Seguidor> crear(@RequestBody Seguidor seguidor) {
        return ResponseEntity.ok(seguidorService.guardar(seguidor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Seguidor> seguidor = seguidorService.obtenerPorId(id);
        if (seguidor.isPresent()) {
            seguidorService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/existe/{seguidorId}/{seguidoId}")
    public ResponseEntity<Boolean> existeRelacion(@PathVariable Integer seguidorId, @PathVariable Integer seguidoId) {
        boolean existe = seguidorService.existeRelacion(seguidorId, seguidoId);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/seguidores/{seguidoId}")
    public List<Seguidor> buscarSeguidoresDeArtista(@PathVariable Integer seguidoId) {
        return seguidorService.buscarSeguidoresDeArtista(seguidoId);
    }

    @GetMapping("/seguidos/{seguidorId}")
    public List<Seguidor> buscarSeguidosPorUsuario(@PathVariable Integer seguidorId) {
        return seguidorService.buscarSeguidosPorUsuario(seguidorId);
    }
}
*/

/*package com.tectoons.tectoonsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.tectoons.tectoonsweb.model.Seguidor;
import com.tectoons.tectoonsweb.service.SeguidorService;
import java.util.List;

@Controller
@RequestMapping("/seguidores")
public class SeguidorController {

    @Autowired
    private SeguidorService seguidorService;

    @GetMapping
    public String listarSeguidores(Model model) {
        List<Seguidor> seguidores = seguidorService.listarTodos();
        model.addAttribute("seguidores", seguidores);
        return "seguidores/listar";
    }

    @GetMapping("/nuevo")
    public String nuevoSeguidor(Model model) {
        model.addAttribute("seguidor", new Seguidor());
        return "seguidores/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarSeguidor(@ModelAttribute Seguidor seguidor) {
        seguidorService.guardar(seguidor);
        return "redirect:/seguidores";
    }

    @GetMapping("/editar/{id}")
    public String editarSeguidor(@PathVariable Long id, Model model) {
        seguidorService.obtenerPorId(id).ifPresent(s -> model.addAttribute("seguidor", s));
        return "seguidores/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarSeguidor(@ModelAttribute Seguidor seguidor) {
        seguidorService.guardar(seguidor);
        return "redirect:/seguidores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSeguidor(@PathVariable Long id) {
        seguidorService.eliminar(id);
        return "redirect:/seguidores";
    }
}*/


package com.tectoons.tectoonsweb.controller;

import com.tectoons.tectoonsweb.model.Seguidor;
import com.tectoons.tectoonsweb.service.SeguidorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/seguidores")
public class SeguidorController {

    @Autowired
    private SeguidorService seguidorService;

    @GetMapping
    public String listarSeguidores(Model model) {
        model.addAttribute("seguidores", seguidorService.listarTodos());
        return "seguidores/listar";
    }

    @GetMapping("/nuevo")
    public String nuevoSeguidor(Model model) {
        model.addAttribute("seguidor", new Seguidor());
        return "seguidores/nuevo";
    }

    @PostMapping("/guardar")
    public String guardarSeguidor(@Valid @ModelAttribute("seguidor") Seguidor seguidor,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Corrige los errores del formulario.");
            return "seguidores/nuevo";
        }
        seguidorService.guardar(seguidor);
        return "redirect:/seguidores";
    }

    @GetMapping("/editar/{id}")
    public String editarSeguidor(@PathVariable Long id, Model model) {
        seguidorService.obtenerPorId(id).ifPresent(s -> model.addAttribute("seguidor", s));
        return "seguidores/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarSeguidor(@Valid @ModelAttribute("seguidor") Seguidor seguidor,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Corrige los errores del formulario.");
            return "seguidores/editar";
        }
        seguidorService.guardar(seguidor);
        return "redirect:/seguidores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSeguidor(@PathVariable Long id) {
        seguidorService.eliminar(id);
        return "redirect:/seguidores";
    }
}
