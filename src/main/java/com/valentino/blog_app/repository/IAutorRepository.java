package com.valentino.blog_app.repository;

import com.valentino.blog_app.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAutorRepository extends JpaRepository<Autor,Long> {

    Optional<Autor> findByUsername(String username);
}
