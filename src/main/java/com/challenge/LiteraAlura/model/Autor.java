package com.challenge.LiteraAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String nombre;
    private int anio_nacimiento;
    private int anio_fallecimiento;

    @Transient
    private List<Libro> libros;



    public Autor(){}

    public Autor(DatosAutor datosAutor){}
}
