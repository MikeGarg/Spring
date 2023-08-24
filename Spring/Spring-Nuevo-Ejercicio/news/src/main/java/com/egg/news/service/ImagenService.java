package com.egg.news.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.egg.news.entities.Imagen;
import com.egg.news.exeptions.MyException;
import com.egg.news.repository.ImagenRepository;

@Service
public class ImagenService {

    @Autowired
    private ImagenRepository imagenRepository;

    public Imagen guardar(MultipartFile archivo) throws MyException {
        if (archivo != null) {
            try {

                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getOriginalFilename());
                imagen.setContenido(archivo.getBytes());

                return imagenRepository.save(imagen);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MyException {
        if (archivo != null) {
            try {

                Imagen imagen = new Imagen();

                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepository.findById(idImagen);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepository.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
	public List<Imagen> listarTodos() {
		return imagenRepository.findAll();
	}

}
