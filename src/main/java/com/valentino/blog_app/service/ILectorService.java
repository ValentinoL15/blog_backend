package com.valentino.blog_app.service;

import com.valentino.blog_app.dto.LectorDTO;
import com.valentino.blog_app.model.Lector;

import java.util.List;
import java.util.Optional;

public interface ILectorService {

    List<LectorDTO> getAllLectors();

    Optional<LectorDTO> getLectorById(Long id);

    Lector saveLector(Lector lector);

    void deleteLectorById(Long id);

}
