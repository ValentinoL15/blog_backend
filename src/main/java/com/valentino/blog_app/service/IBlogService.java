package com.valentino.blog_app.service;

import com.valentino.blog_app.dto.BlogDTO;
import com.valentino.blog_app.dto.CreateBlogDTO;
import com.valentino.blog_app.model.Blog;

import java.util.List;
import java.util.Optional;

public interface IBlogService {

    List<BlogDTO> getBlogs();

    List<BlogDTO> getBlogByEtiqueta(String name);

    List<BlogDTO> getMyBlogs();

    Optional<BlogDTO> getBlogById(Long id);

    BlogDTO createBlog(CreateBlogDTO blogDTO);

    void deleteBlogById(Long id);

    Blog updateBlog(Long id, BlogDTO blog);

}
