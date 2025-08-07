package com.valentino.blog_app.controller;

import com.valentino.blog_app.dto.AutorDTO;
import com.valentino.blog_app.model.Autor;
import com.valentino.blog_app.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private IAutorService autorService;

    @PreAuthorize("hasAnyRole('ADMIN', 'LECTOR', 'AUTOR')")
    @GetMapping
    public ResponseEntity<List<AutorDTO>> getAutores() {
        try {
            List<AutorDTO> autoresList = autorService.getAllAutores();
            return ResponseEntity.ok(autoresList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LECTOR', 'AUTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> getAutor(@PathVariable Long id) {
        try {
            Optional<AutorDTO> autor = autorService.getAutorById(id);
            return autor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
