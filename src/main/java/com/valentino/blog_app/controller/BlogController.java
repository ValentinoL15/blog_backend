package com.valentino.blog_app.controller;

import com.valentino.blog_app.dto.BlogDTO;
import com.valentino.blog_app.dto.CreateBlogDTO;
import com.valentino.blog_app.dto.ErrorDTO;
import com.valentino.blog_app.dto.GetAutorDTO;
import com.valentino.blog_app.model.Autor;
import com.valentino.blog_app.model.Blog;
import com.valentino.blog_app.model.UserSec;
import com.valentino.blog_app.repository.IAutorRepository;
import com.valentino.blog_app.service.IAutorService;
import com.valentino.blog_app.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private IAutorRepository autorRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<BlogDTO>> getBlogs() {
        try {
            List<BlogDTO> blogList = blogService.getBlogs();
            return ResponseEntity.ok(blogList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlog(@PathVariable Long id){
        try {
            Optional<BlogDTO> blog = blogService.getBlogById(id);
            return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/contenido/{name}")
    public ResponseEntity<?> blogs(@PathVariable String name) {
        try {
            List<BlogDTO> blogs = blogService.getBlogByEtiqueta(name);
            return ResponseEntity.ok(blogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('AUTOR')")
    @PostMapping("/crear-blog")
    public ResponseEntity<BlogDTO> createBlog(@RequestBody CreateBlogDTO blogDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Usuario autenticado: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());

        try {
            BlogDTO blog1 = blogService.createBlog(blogDTO);
            return ResponseEntity.ok(blog1);
        } catch (Exception e) {
            e.printStackTrace(); // para ver el error real
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('AUTOR')")
    @PutMapping("/editar-blog/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Long id,
                                              @RequestBody BlogDTO blogDTO) {
        try {
            Blog blog = blogService.updateBlog(id,blogDTO);
            Autor autor = autorRepository.findById(blog.getAutor().getUser_id())
                    .orElseThrow(() -> new RuntimeException("No se encuentra el autor"));

            BlogDTO responseDTO = new BlogDTO(
                    blog.getBlog_id(),
                    blog.getTitulo(),
                    blog.getContenido(),
                    blog.getFecha_lanzamiento(),
                    blog.getEtiqueta(),
                    new GetAutorDTO(
                            blog.getAutor().getUsername()
                    )
            );

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDTO(e.getMessage()));
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myBlogs")
    public ResponseEntity<?> getMyBlogs() {
        try {
            List<BlogDTO> myBlogs = blogService.getMyBlogs();
            return ResponseEntity.ok(myBlogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDTO(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTOR')")
    @DeleteMapping("/eliminar-blog/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id){
        try {
            blogService.deleteBlogById(id);
            return ResponseEntity.noContent().build(); // HTTP 204
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getMessage()));
        }
    }

}
