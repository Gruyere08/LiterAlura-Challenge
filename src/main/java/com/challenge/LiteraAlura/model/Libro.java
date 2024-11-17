package com.challenge.LiteraAlura.model;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.id_guten = datosLibro.id_guten();
        this.titulo = datosLibro.titulo();
        this.temas = datosLibro.temas();
        this.lenguajes = datosLibro.lenguajes();
        this.descargas = datosLibro.descargas();
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores != null ? autores : new HashSet<>();
        this.autores.forEach(a -> a.addLibro(this));
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public long getId_guten() {
        return id_guten;
    }

    public void setId_guten(long id_guten) {
        this.id_guten = id_guten;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getTemas() {
        return temas;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }

    public List<String> getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(List<String> lenguajes) {
        this.lenguajes = lenguajes;
    }

    public long getDescargas() {
        return descargas;
    }

    public void setDescargas(long descargas) {
        this.descargas = descargas;
    }
}
