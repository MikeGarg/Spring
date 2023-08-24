package com.egg.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egg.news.entities.Noticia;

public interface NoticiaRepository extends JpaRepository<Noticia,String>{
    
    @Query("SELECT n FROM Noticia n WHERE n.id = :id")
    public Noticia buscarPorId(@Param("id") String id);

    @Query("SELECT n FROM Noticia n WHERE n.titulo = :titulo")
    public Noticia buscarPorTitulo(@Param("titulo") String titulo);

}
