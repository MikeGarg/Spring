package com.egg.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.news.entities.Noticia;
import com.egg.news.service.NoticiaService;

@Controller
@RequestMapping("/imagen")
public class ImagenController {
    
    @Autowired
    private NoticiaService noticiaService;

    @GetMapping("/noticia/{id}")
    public  ResponseEntity<byte[]> imagenNoticia(@PathVariable String id){
        
        Noticia noticia = noticiaService.getOne(id);

        byte[] imagen = noticia.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imagen,headers, HttpStatus.OK);
    }


}
