package com.egg.news.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.egg.news.entities.Imagen;
import com.egg.news.entities.Noticia;
import com.egg.news.exeptions.MyException;
import com.egg.news.repository.NoticiaRepository;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;
    @Autowired
    private ImagenService imagenService;

    @Transactional
    public void crearNoticia(String seccion, String titulo, String cuerpo, MultipartFile archivo) throws MyException {

        validate(seccion, titulo, cuerpo);

        Noticia noticia = new Noticia();
        noticia.setSeccion(seccion);
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);

        Imagen imagen = imagenService.guardar(archivo);
        noticia.setImagen(imagen);

        noticia.setAlta(true);
        noticiaRepository.save(noticia);
    }

    @Transactional(readOnly = true)
    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = new ArrayList<Noticia>();
        noticias = noticiaRepository.findAll();
        return noticias;
    }

    @Transactional
    public void modificarNoticia(String id, String seccion, String titulo, String cuerpo, MultipartFile archivo)
            throws MyException {

        validate(seccion, titulo, cuerpo);

        Optional<Noticia> noticiaOptional = noticiaRepository.findById(id);

        if (noticiaOptional.isPresent()) {
            Noticia noticia = noticiaOptional.get();

            noticia.setSeccion(seccion);
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);

            String idImagen = null;

            if (noticia.getImagen() != null) {
                idImagen = noticia.getImagen().getId();
            }

            Imagen imagen = imagenService.actualizar(archivo, idImagen);

            noticia.setImagen(imagen);

            noticiaRepository.save(noticia);
        }
    }

    @Transactional
    public void eliminarNoticia(String id) throws MyException {

        if (id.isEmpty()){
            throw new MyException("El id no puede estar vacio");
        }

        try {
            Optional<Noticia> noticiaOptional = noticiaRepository.findById(id);

        if (noticiaOptional.isPresent()){

            Noticia noticia = noticiaOptional.get();

            noticiaRepository.delete(noticia);
        }else{
            throw new MyException("No se encontro la Noticia");
        }

        } catch (Exception e) {
            throw e;
        }
        
        
    }

    @Transactional
    public void altaBajaNoticia(String id, boolean alta) {

        Optional<Noticia> noticiaOptional = noticiaRepository.findById(id);

        if (noticiaOptional.isPresent()) {
            Noticia noticia = noticiaOptional.get();

            noticia.setAlta(alta);

            noticiaRepository.save(noticia);
        }
    }

    public Noticia getOne(String Id) {
        return noticiaRepository.getReferenceById(Id);
    }

    private void validate(String seccion, String titulo, String cuerpo) throws MyException {

        if (seccion.isEmpty()) {
            throw new MyException("Seccion no puede estar vacio");
        }
        if (titulo.isEmpty()) {
            throw new MyException("Titulo no puede estar vacio");
        }
        if (cuerpo.isEmpty()) {
            throw new MyException("Cuerpo no puede estar vacio");
        }
    }

}
