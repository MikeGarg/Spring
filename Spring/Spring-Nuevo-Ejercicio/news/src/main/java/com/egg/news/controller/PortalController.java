package com.egg.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.news.entities.Noticia;
import com.egg.news.entities.Usuario;
import com.egg.news.service.NoticiaService;
import com.egg.news.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PortalController {

    @Autowired
    private NoticiaService noticiaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String index(ModelMap model) {

        List<Noticia> noticias = noticiaService.listarNoticias();
        model.addAttribute("noticias", noticias);

        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "usuario_registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String name, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2,
            ModelMap model) {

        try {

            usuarioService.registrar(name, email, password, password2);

            model.put("exito", "Usuario registrado correctamente!");

            return "index.html";

        } catch (Exception e) {

            model.put("error", e.getMessage());
            model.put("name", name);
            model.put("email", email);

            return "redirect:/registrar";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {

        if (error != null) {
            model.put("error", "Usuario o Contraseña invalidos!");
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_PERIODISTA')")
    @GetMapping("/inicio")
    public String inicio(ModelMap model, HttpSession session) {

        Usuario userLogged = (Usuario) session.getAttribute("usuario");

        List<Noticia> noticias = noticiaService.listarNoticias();
        model.addAttribute("noticias", noticias);

        if (userLogged.getRole().toString().equals("ADMIN")) {
            return "redirect:/noticia/lista";
        }

        return "inicio.html";
    }

}
