package com.egg.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egg.news.entities.Imagen;

public interface ImagenRepository extends JpaRepository<Imagen, String>{
    
}
