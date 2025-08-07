package com.valentino.blog_app.service;

import com.valentino.blog_app.dto.AutorDTO;
import com.valentino.blog_app.model.Autor;

import java.util.List;
import java.util.Optional;

public interface IAutorService {

    List<AutorDTO> getAllAutores();

    Optional<AutorDTO> getAutorById(Long id);

    Autor saveAutor(Autor autor);

    void deleteAutorById(Long id);

    Autor updateAutor(Long id, Autor autor);

}
