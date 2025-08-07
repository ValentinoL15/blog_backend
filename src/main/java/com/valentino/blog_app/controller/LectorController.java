package com.valentino.blog_app.controller;

import com.valentino.blog_app.dto.LectorDTO;
import com.valentino.blog_app.model.Lector;
import com.valentino.blog_app.service.ILectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lectores")
public class LectorController {

    @Autowired
    private ILectorService lectorService;

    @PreAuthorize("hasAnyRole('ADMIN', 'LECTOR', 'AUTOR')")
    @GetMapping
    public ResponseEntity<List<LectorDTO>> getLectores() {
        try {
            List<LectorDTO> lectores = lectorService.getAllLectors();
            return ResponseEntity.ok(lectores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LECTOR', 'AUTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<LectorDTO> getLector(@PathVariable Long id) {
        try {
            Optional<LectorDTO> lector = lectorService.getLectorById(id);
            return lector.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar-lector/{id}")
    public ResponseEntity<String> deleteLector(@PathVariable Long id) {
        try {
            lectorService.deleteLectorById(id);
            return ResponseEntity.ok("Lector eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
