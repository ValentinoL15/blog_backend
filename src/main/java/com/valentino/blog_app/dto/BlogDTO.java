package com.valentino.blog_app.dto;

import com.valentino.blog_app.model.Autor;
import com.valentino.blog_app.model.enums.EtiquetaBlog;

import java.util.Date;

public record BlogDTO(Long blog_id,
                      String titulo,
                      String contenido,
                      Date fecha_lanzamiento,
                      EtiquetaBlog etiqueta,
                      GetAutorDTO autor) {
}
