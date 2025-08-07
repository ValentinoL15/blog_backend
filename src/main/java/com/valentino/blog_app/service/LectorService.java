package com.valentino.blog_app.service;

import com.valentino.blog_app.dto.LectorDTO;
import com.valentino.blog_app.model.Lector;
import com.valentino.blog_app.repository.ILectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LectorService implements ILectorService{

    @Autowired
    private ILectorRepository lectorRepository;

    @Override
    public List<LectorDTO> getAllLectors() {
        return lectorRepository.findAll().stream()
                .map(lector -> new LectorDTO(lector.getUsername(), lector.getRolesList()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LectorDTO> getLectorById(Long id) {
        return lectorRepository.findById(id)
                .map(lector -> new LectorDTO(lector.getUsername(),lector.getRolesList()));
    }

    @Override
    public Lector saveLector(Lector lector) {
        return lectorRepository.save(lector);
    }

    @Override
    public void deleteLectorById(Long id) {
        lectorRepository.deleteById(id);
    }
}
