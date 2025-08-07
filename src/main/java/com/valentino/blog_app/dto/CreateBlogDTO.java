package com.valentino.blog_app.dto;

import com.valentino.blog_app.model.Autor;
import com.valentino.blog_app.model.enums.EtiquetaBlog;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CreateBlogDTO(@NotBlank String titulo,
                            @NotBlank String contenido,
                            @NotNull Date fecha_lanzamiento,
                            @NotNull EtiquetaBlog etiqueta) {
}
