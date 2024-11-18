package com.challenge.LiteraAlura.model;

import com.challenge.LiteraAlura.otros.Color;
import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    //@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private Set<Libro> libros = new HashSet<>();

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.anio_nacimiento = datosAutor.anio_nacimiento();
        this.anio_fallecimiento = datosAutor.anio_fallecimiento();
    }

    @Override
    public String toString() {
        return "---------- AUTOR ---------- \n"
                + Color.CYAN + "Nombre: " + nombre + "\n"
                + Color.VERDE_CLARO + "Periodo: "
                + (anio_nacimiento != null ? anio_nacimiento : "DESCONOCIDO") + " - "
                + (anio_fallecimiento != null ? anio_fallecimiento : "DESCONOCIDO") + "\n"
                + Color.MORADO + "Libros populares: " + topLibrosToString(5) + Color.RESET;
    }


    public String topLibrosToString(int limite){
        return libros.stream()
                .sorted(Comparator.comparingLong(Libro::getDescargas).reversed())  // Sort by descargas in descending order
                .limit(limite)  // Limit the result to top 5
                .map(Libro::getTitulo)  // Extract the "titulo" of each libro
                .collect(Collectors.joining("; "));
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
