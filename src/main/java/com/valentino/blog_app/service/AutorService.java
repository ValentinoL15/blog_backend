package com.valentino.blog_app.service;

import com.valentino.blog_app.dto.AutorDTO;
import com.valentino.blog_app.dto.AutorSimpleDTO;
import com.valentino.blog_app.dto.BlogDTO;
import com.valentino.blog_app.dto.GetAutorDTO;
import com.valentino.blog_app.model.Autor;
import com.valentino.blog_app.repository.IAutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AutorService implements IAutorService {

    @Autowired
    private IAutorRepository autorRepository;

    @PreAuthorize("hasAnyRole('ADMIN', 'LECTOR', 'AUTOR')")
    @Override
    public List<AutorDTO> getAllAutores() {
        return autorRepository.findAll().stream()
                .map(user -> {
                    Set<BlogDTO> blogs = user.getBlogsList().stream()
                            .map(blog -> new BlogDTO(
                                    blog.getBlog_id(),
                                    blog.getTitulo(),
                                    blog.getContenido(),
                                    blog.getFecha_lanzamiento(),
                                    blog.getEtiqueta(),
                                    new GetAutorDTO(
                                            blog.getAutor().getUsername()
                                    )
                                    //new AutorSimpleDTO(user.getUsername())
                            ))
                            .collect(Collectors.toSet());

                    return new AutorDTO(user.getUser_id(),user.getUsername(),blogs,user.getRolesList());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AutorDTO> getAutorById(Long id) {
        return autorRepository.findById(id)
                .map(autor -> {
                    Set<BlogDTO> blogs = autor.getBlogsList().stream()
                            .map(blog -> new BlogDTO(
                                    blog.getBlog_id(),
                                    blog.getTitulo(),
                                    blog.getContenido(),
                                    blog.getFecha_lanzamiento(),
                                    blog.getEtiqueta(),
                                    new GetAutorDTO(
                                            blog.getAutor().getUsername()
                                    )
                                    //new AutorSimpleDTO(autor.getUsername())
                            ))
                            .collect(Collectors.toSet());

                    return new AutorDTO(autor.getUser_id(),autor.getUsername(), blogs, autor.getRolesList());
                });
    }

    @Override
    public Autor saveAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    @Override
    public void deleteAutorById(Long id) {
        autorRepository.deleteById(id);
    }

    @Override
    public Autor updateAutor(Long id, Autor autor) {
        Autor existingAutor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el autor"));
        if(autor.getUsername() != null) autor.setUsername(autor.getUsername());
        return autorRepository.save(existingAutor);
    }
}
