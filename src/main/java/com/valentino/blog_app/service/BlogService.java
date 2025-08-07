package com.valentino.blog_app.service;

import com.valentino.blog_app.dto.BlogDTO;
import com.valentino.blog_app.dto.CreateBlogDTO;
import com.valentino.blog_app.dto.GetAutorDTO;
import com.valentino.blog_app.model.Autor;
import com.valentino.blog_app.model.Blog;
import com.valentino.blog_app.model.UserSec;
import com.valentino.blog_app.model.enums.EtiquetaBlog;
import com.valentino.blog_app.repository.IAutorRepository;
import com.valentino.blog_app.repository.IBlogRepository;
import com.valentino.blog_app.repository.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogService implements IBlogService{

    @Autowired
    private IBlogRepository blogRepository;

    @Autowired
    private IAutorRepository autorRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<BlogDTO> getBlogs() {
        List<Blog> myBlogs = blogRepository.findAll();
        return myBlogs.stream().map(blog -> new BlogDTO(
                blog.getBlog_id(),
                blog.getTitulo(),
                blog.getContenido(),
                blog.getFecha_lanzamiento(),
                blog.getEtiqueta(),
                new GetAutorDTO(blog.getAutor().getUsername())
        ))
                .collect(Collectors.toList());
    }

    @Override
    public List<BlogDTO> getBlogByEtiqueta(String name) {
        EtiquetaBlog etiqueta = EtiquetaBlog.valueOf(name.toUpperCase());

    return blogRepository.findByEtiqueta(etiqueta)
            .stream().map(blog -> new BlogDTO(
                    blog.getBlog_id(),
                    blog.getTitulo(),
                    blog.getContenido(),
                    blog.getFecha_lanzamiento(),
                    blog.getEtiqueta(),
                    new GetAutorDTO(
                            blog.getAutor().getUsername()
                    )
            ))
            .collect(Collectors.toList());
    }

    @Override
    public List<BlogDTO> getMyBlogs() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Blog> blogslist = blogRepository.findAll();
        Set<Blog> myBlogs = new HashSet<>();

        for(Blog blog: blogslist) {
            if(blog.getAutor().getUsername().equals(username)){
                myBlogs.add(blog);
            }
        }
        return myBlogs.stream()
                .map(blog -> new BlogDTO(
                        blog.getBlog_id(),
                        blog.getTitulo(),
                        blog.getContenido(),
                        blog.getFecha_lanzamiento(),
                        blog.getEtiqueta(),
                        new GetAutorDTO(blog.getAutor().getUsername())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BlogDTO> getBlogById(Long id) {

        Blog blog = blogRepository.findById(id).orElseThrow(()-> new RuntimeException("No se encuentra el blog"));
        return Optional.of(new BlogDTO(
                blog.getBlog_id(),
                blog.getTitulo(),
                blog.getContenido(),
                blog.getFecha_lanzamiento(),
                blog.getEtiqueta(),
                new GetAutorDTO(
                        blog.getAutor().getUsername()
                ))
        );
    }

    @Override
    public BlogDTO createBlog(CreateBlogDTO blogDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("petee" + username);
        List<Autor> autores = autorRepository.findAll();
        Autor myAutor = null;
        for (Autor aut : autores) {
            if (aut.getUsername().equals(username)) {
                myAutor = aut;
                break;
            }
        }

        Blog blog = new Blog();
        blog.setTitulo(blogDTO.titulo());
        blog.setAutor(myAutor);
        blog.setContenido(blogDTO.contenido());
        blog.setFecha_lanzamiento(blogDTO.fecha_lanzamiento());
        blog.setEtiqueta(blogDTO.etiqueta());

        Blog myBlog = blogRepository.save(blog);
        return new BlogDTO(
                myBlog.getBlog_id(),
                myBlog.getTitulo(),
                myBlog.getContenido(),
                myBlog.getFecha_lanzamiento(),
                myBlog.getEtiqueta(),
                new GetAutorDTO(
                        myBlog.getAutor().getUsername()
                )
        );
    }

    @Override
    public void deleteBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("No se encuentra el blog"));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //Autor autorAuth = autorRepository.findByUsername(username)
          //              .orElseThrow(() -> new RuntimeException("No se encuentra el autor"));
        UserSec userAuth = userRepository.findUserEntityByUsername(username)
                        .orElseThrow(() -> new RuntimeException("No se encuentra el user"));

        Optional<Autor> optionalAutor = autorRepository.findByUsername(username);

        boolean isAutor = optionalAutor
                .map(autor -> blog.getAutor().getUser_id().equals(autor.getUser_id()))
                        .orElse(false);

        boolean isAdmin = userAuth.getRolesList().stream()
                        .anyMatch(rol -> rol.getRole().equalsIgnoreCase("ADMIN"));

        if (!isAutor && !isAdmin) {
            throw new RuntimeException("No tienes permisos para editar este blog");
        }

        blogRepository.deleteById(id);
    }

    @Override
    public Blog updateBlog(Long id, BlogDTO blogDTO) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encuentra el blog"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Autor autorAuth = autorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No se encuentra el autor"));

        if(!blog.getAutor().getUser_id().equals(autorAuth.getUser_id())) {
            throw new RuntimeException("No tienes permisos para editar este blog");
        }

        if(blogDTO.titulo() != null) blog.setTitulo(blogDTO.titulo());
        if(blogDTO.contenido() != null) blog.setContenido(blogDTO.contenido());
        if(blogDTO.fecha_lanzamiento() != null) blog.setFecha_lanzamiento(blogDTO.fecha_lanzamiento());
        if(blogDTO.etiqueta() != null) blog.setEtiqueta(blogDTO.etiqueta());

        return blogRepository.save(blog);
    }
}
