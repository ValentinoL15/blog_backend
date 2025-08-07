package com.valentino.blog_app.repository;

import com.valentino.blog_app.model.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILectorRepository extends JpaRepository<Lector,Long> {
}
