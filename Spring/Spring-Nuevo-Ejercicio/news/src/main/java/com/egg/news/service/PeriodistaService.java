package com.egg.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.news.entities.Periodista;
import com.egg.news.enums.Role;
import com.egg.news.exeptions.MyException;
import com.egg.news.repository.PeriodistaRepository;

@Service
public class PeriodistaService {

    @Autowired
    private PeriodistaRepository periodistaRepository;

    @Transactional
    public void crearPeriodista(String name, String email, String password, String password2)
            throws MyException {

        Periodista periodista = new Periodista();

        periodista.setName(name);
        periodista.setEmail(email);

        periodista.setPassword(new BCryptPasswordEncoder().encode(password));

        periodista.setRole(Role.PERIODISTA);
        periodista.setActive(true);

        periodistaRepository.save(periodista);
    }

}
