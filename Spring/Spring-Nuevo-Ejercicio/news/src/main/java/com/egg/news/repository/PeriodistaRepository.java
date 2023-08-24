package com.egg.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egg.news.entities.Periodista;

public interface PeriodistaRepository extends JpaRepository<Periodista, String> {

}
