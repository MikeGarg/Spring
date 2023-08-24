package com.egg.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egg.news.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario searchByEmail(@Param("email") String email);
}
