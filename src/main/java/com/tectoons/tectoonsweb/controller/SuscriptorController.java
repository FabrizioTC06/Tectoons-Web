package com.tectoons.tectoonsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tectoons.tectoonsweb.service.SuscriptorService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SuscriptorController {
    @Autowired
    private SuscriptorService suscriptorService;

    @PostMapping("/suscribirse")
    public String suscribirse(
            @RequestParam String email,
            HttpServletRequest request,
            RedirectAttributes attr) {
        try {
            // Guardar en BD
            suscriptorService.guardar(email);

            attr.addFlashAttribute("suscripcionOk", "Correo enviado. Muchas gracias.");
        } catch (Exception e) {
            attr.addFlashAttribute("suscripcionError", "Hubo un error al guardar el correo.");
        }

        String url = request.getHeader("Referer");
        return "redirect:" + url;
    }

}
