package com.valentino.blog_app.service;

import com.valentino.blog_app.model.Autor;
import com.valentino.blog_app.model.Role;
import com.valentino.blog_app.model.UserSec;
import com.valentino.blog_app.repository.IRoleRespository;
import com.valentino.blog_app.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private IUserRepository userSecRepo;

    @Autowired
    private IRoleRespository roleRespository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        System.out.println("Atributos recibidos: " + oAuth2User.getAttributes());

        String username = oAuth2User.getAttribute("username");

        if (username == null) {
            throw new OAuth2AuthenticationException("No se pudo obtener el email del proveedor OAuth2");
        }

        Role rol = roleRespository.findById(4L)
                .orElseThrow(() -> new RuntimeException("No se encuntra ese rol"));


        UserSec user = userSecRepo.findUserEntityByUsername(username)
                .orElseGet(() -> {

                    Autor nuevoAutor = new Autor();
                    nuevoAutor.setUsername(username);
                    nuevoAutor.setEnabled(false);
                    nuevoAutor.setAccountNotExpired(false);
                    nuevoAutor.setAccountNotLocked(false);
                    nuevoAutor.setCredentialNotExpired(false);
                    nuevoAutor.setRolesList(Set.of(rol));
                    return userSecRepo.save(nuevoAutor);  // Guarda como Autor
                });
        // Creamos un nuevo map con los atributos originales + nuestro username
        Map<String, Object> atributos = new HashMap<>(oAuth2User.getAttributes());
        atributos.put("username", user.getUsername()); // aseguramos que esté

        return new DefaultOAuth2User(
                user.getRolesList().stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole()))
                        .collect(Collectors.toSet()),
                atributos,
                "username" // ← ← ← ESTE ES EL CAMBIO IMPORTANTE
        );
    }
}
