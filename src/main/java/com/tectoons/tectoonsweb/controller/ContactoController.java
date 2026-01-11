package com.tectoons.tectoonsweb.controller;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import com.tectoons.tectoonsweb.model.Contacto;
import com.tectoons.tectoonsweb.service.ContactoService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contacto")
public class ContactoController {

    private final ContactoService contactoService;

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("contacto", new Contacto());
        return "contacto";
    }

    @PostMapping
    public String enviarFormulario(@Valid @ModelAttribute("contacto") Contacto contacto,
                                   BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            return "contacto";
        }

        contactoService.guardar(contacto);
        model.addAttribute("success", "✅ Tu mensaje fue enviado correctamente");
        model.addAttribute("contacto", new Contacto()); // limpiar formulario
        return "contacto";
    }
}
