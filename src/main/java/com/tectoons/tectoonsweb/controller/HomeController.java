package com.tectoons.tectoonsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Página principal
    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html
    }

    @GetMapping("/index1")
    public String index1() {
        return "index1"; // templates/index.html
    }

    @GetMapping("/artista/index")
    public String indexartista() {
        return "/artista/index"; // templates/index.html
    }

    @GetMapping("/index2")
    public String index2() {
        return "index2"; // templates/index.html
    }

    @GetMapping("/index-carousel")
    public String index3() {
        return "index-carousel"; // templates/index.html
    }

    @GetMapping("/navbar-test")
    public String navbar() {
        return "fragments/header"; // para probar solo el header
    }

    
    @GetMapping("/test-header")
    public String testHeader(Model model) {
        // Agregar datos de prueba para el header
        model.addAttribute("titulo", "TecToons - Prueba Header");
        
        // Datos simulados del usuario (para probar diferentes estados)
        // model.addAttribute("userAvatar", "/img/avatar-test.png");
        // model.addAttribute("isAuthenticated", true);
        // model.addAttribute("cartCount", 5);
        
        return "test-header"; // Esto renderizará templates/test-header.html
    }

    @GetMapping("/portafolio2")
    public String portafolio() {
        return "portafolio";
    }
    
@GetMapping("/tienda1")
    public String tienda() {
        return "tienda";
    }

    // Artistas
    @GetMapping("/artistas1")
    public String artistas() {
        return "artistas";
    }

    // Detalle artista
  /*  @GetMapping("/artistas/detalle1")
    public String detalleArtista() {
        return "detalle-artista";
    }*/


    @GetMapping("/terminos-condiciones")
    public String terminosCondiciones() {
        return "terminos-condiciones";
    }

    @GetMapping("/preguntas-frecuentes")
    public String preguntasFrecuentes() {
        return "preguntas-frecuentes";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

   /* @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }*/

@GetMapping("/detalle-artista")
    public String detalleArtista() {
        return "detalle-artista";
    }
 /*    // Contacto
    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @GetMapping("/terminos-condiciones")
    public String terminosCondiciones() {
        return "terminos-condiciones";
    }
*/

/* 
    // Tienda
    @GetMapping("/tienda")
    public String tienda() {
        return "tienda";
    }

    // Artistas
    @GetMapping("/artistas")
    public String artistas() {
        return "artistas";
    }

    // Detalle artista
    @GetMapping("/artistas/detalle")
    public String detalleArtista() {
        return "detalle_artista";
    }

    // Portafolio
    @GetMapping("/portafolio")
    public String portafolio() {
        return "portafolio";
    }

    // Contacto
    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    // Blog
    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    // Login y registro
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "auth/registro";
    }

    // Solicitud para ser artista
    @GetMapping("/solicitud_artista")
    public String solicitudArtista() {
        return "usuarios/solicitud_artista";
    }

    // Página de error (opcional)
    @GetMapping("/error")
    public String error() {
        return "error/error";
    }*/
}
