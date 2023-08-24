package com.egg.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.egg.news.entities.Noticia;
import com.egg.news.exeptions.MyException;
import com.egg.news.service.NoticiaService;

@Controller
@RequestMapping("/noticia")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @GetMapping("/registrar")
    public String registrar() {

        return "noticia_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String seccion, @RequestParam String titulo, @RequestParam String cuerpo,
            ModelMap model, @RequestParam MultipartFile archivo) {

        try {

            noticiaService.crearNoticia(seccion, titulo, cuerpo, archivo);
            model.put("exito", "La Noticia se cargó con éxito!");

        } catch (Exception e) {

            model.put("error", e.getMessage());
            return "noticia_form.html";
        }

        return "noticia_form.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap model) {

        List<Noticia> noticias = noticiaService.listarNoticias();
        model.addAttribute("noticias", noticias);

        return "noticia_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap model) {

        model.put("noticia", noticiaService.getOne(id));

        return "noticia_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String seccion, String titulo, String cuerpo, ModelMap model) {
        try {

            noticiaService.modificarNoticia(id, seccion, titulo, cuerpo, null);
            model.put("exito", "La Noticia de Modifico con éxito!");

            return "redirect:../lista";

        } catch (MyException e) {
            model.put("error", e.getMessage());

            return "libro_modificar.html";
        }

    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap model) {
        try {

            noticiaService.eliminarNoticia(id);
            model.put("exito", "La Noticia de Eliminó con éxito!");

            return "redirect:../lista";

        } catch (MyException e) {
            model.put("error", e.getMessage());

            return "index.html";
        }

    }

    @GetMapping("/baja")
    public String baja() {
        return "noticia_form.html";
    }

}
