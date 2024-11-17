package com.challenge.LiteraAlura.model;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String nombre;
    @Column(nullable = true)
    private Integer anio_nacimiento;
    @Column(nullable = true)
    private Integer anio_fallecimiento;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private Set<Libro> libros = new HashSet<>();



    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.anio_nacimiento = datosAutor.anio_nacimiento();
        this.anio_fallecimiento = datosAutor.anio_fallecimiento();
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void addLibro(Libro libro) {
        if (this.libros == null) {
            this.libros = new HashSet<>();
        }
        this.libros.add(libro);
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio_nacimiento() {
        return anio_nacimiento;
    }

    public void setAnio_nacimiento(int anio_nacimiento) {
        this.anio_nacimiento = anio_nacimiento;
    }

    public int getAnio_fallecimiento() {
        return anio_fallecimiento;
    }

    public void setAnio_fallecimiento(int anio_fallecimiento) {
        this.anio_fallecimiento = anio_fallecimiento;
    }
}
