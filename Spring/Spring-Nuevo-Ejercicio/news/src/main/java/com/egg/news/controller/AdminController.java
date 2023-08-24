package com.egg.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.news.entities.Usuario;
import com.egg.news.service.UsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/dashboard")
    public String adminPanel() {
        return "panel.html";
    }

    @GetMapping("/usuario/list")
    public String usuarioList(ModelMap model){

        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);

        return "usuario_list.html";
    }

}
