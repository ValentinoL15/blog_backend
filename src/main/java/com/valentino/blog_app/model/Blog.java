package com.valentino.blog_app.model;

import com.valentino.blog_app.model.enums.EtiquetaBlog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blog_id;
    private String titulo;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String contenido;
    private Date fecha_lanzamiento;
    @Enumerated(EnumType.STRING)
    private EtiquetaBlog etiqueta;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

}
