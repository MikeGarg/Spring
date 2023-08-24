package com.egg.news.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Periodista extends Usuario{

    private Integer sueldoMensual;

    @OneToMany
    private List<Noticia> noticias;
    
    public Periodista() {
        this.noticias = new ArrayList<Noticia>();
        this.sueldoMensual = 0;
    }

    public Integer getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(Integer sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }

    public List<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    
    
}
