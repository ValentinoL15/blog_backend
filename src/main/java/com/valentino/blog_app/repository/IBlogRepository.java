package com.valentino.blog_app.repository;

import com.valentino.blog_app.model.Blog;
import com.valentino.blog_app.model.enums.EtiquetaBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogRepository extends JpaRepository<Blog,Long> {

    List<Blog> findByEtiqueta(EtiquetaBlog etiqueta);

}
