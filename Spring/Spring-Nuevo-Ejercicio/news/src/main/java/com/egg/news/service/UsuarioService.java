package com.egg.news.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.egg.news.entities.Usuario;
import com.egg.news.enums.Role;
import com.egg.news.exeptions.MyException;
import com.egg.news.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void registrar(String name, String email, String password, String password2) throws MyException {

        validar(name, email, password, password2);

        Usuario user = new Usuario();
        user.setName(name);
        user.setEmail(email);

        user.setPassword(new BCryptPasswordEncoder().encode(password));

        user.setRole(Role.USER);
        user.setActive(true);

        usuarioRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.searchByEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRole().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuario", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    private void validar(String name, String email, String password, String password2) throws MyException {

        if (name.isEmpty() || name == null) {
            throw new MyException("El nombre de puede ser nulo o estar vacío!");
        }

        if (email.isEmpty() || email == null) {
            throw new MyException("El email de puede ser nulo o estar vacío!");
        }

        if (password.isEmpty() || password == null || password.length() < 6) {
            throw new MyException("La contraseña debe contener mas de 5 digitos!");
        }

        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas ingresadas deben ser iguales!");
        }
    }
}
