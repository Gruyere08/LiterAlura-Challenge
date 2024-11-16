package com.challenge.LiteraAlura.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long id_guten;
    @Column(unique = true, nullable = false)
    private String titulo;
    private List<String> temas;
    private List<String> lenguajes;
    private long descargas;

    @Transient
    private List<Autor> autores;

    public Libro(){}

    public Libro(DatosLibro datosLibro){}

}
