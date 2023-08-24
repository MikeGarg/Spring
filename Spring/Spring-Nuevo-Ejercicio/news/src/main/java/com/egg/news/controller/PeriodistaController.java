package com.egg.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.news.service.PeriodistaService;

@Controller
@RequestMapping("/periodista")
public class PeriodistaController {

    @Autowired
    private PeriodistaService periodistaService;

    @GetMapping("/registrar")
    public String registrar() {
        return "periodista_registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String name, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2,
            ModelMap model) {

        try {
            periodistaService.crearPeriodista(name, email, password, password2);
            model.put("exito", "Usuario registrado correctamente!");

            return "redirect:/";

        } catch (Exception e) {

            model.put("error", e.getMessage());
            model.put("name", name);
            model.put("email", email);

            return "redirect:/registrar";
        }

        
    }

}
