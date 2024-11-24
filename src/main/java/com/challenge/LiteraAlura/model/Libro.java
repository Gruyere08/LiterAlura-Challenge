package com.challenge.LiteraAlura.model;
import com.challenge.LiteraAlura.otros.Color;
import com.challenge.LiteraAlura.otros.Lenguaje;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long id_guten;
    @Column(length = 1000, unique = true, nullable = false)
    private String titulo;
    private List<String> temas;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "libros_lenguajes", // Name of the secondary table
            joinColumns = @JoinColumn(name = "libro_id") // Foreign key to the primary table
    )
    @Column(name = "lenguaje") // Name of the column storing the language values
    private List<String> lenguajes = new ArrayList<>(); // Initializes the collection
    private long descargas;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.EAGER)
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


    @Override
    public String toString() {
        return "---------- LIBRO ---------- \n"+
                Color.AZUL +"Titulo: " + titulo + "\n"+
                Color.CYAN+ "Autores: " + autoresToString() + "\n" +
                Color.MORADO +"Descargas: " + descargas + Color.RESET;
    }


    public String toStringConLenguajes() {
        return "---------- LIBRO ---------- \n"+
                Color.AZUL +"Titulo: " + titulo + "\n"+
                Color.CYAN+ "Autores: " + autoresToString() + "\n" +
                Color.VERDE_CLARO +"Lenguajes: " + lenguajesToString() + Color.RESET;
    }


    public String toStringSeleccion() {
        return Color.AZUL + this.titulo + "\n" +
                Color.CYAN + "----------------- POR: " + this.autoresToString() + Color.RESET;
    }

    public String lenguajesToString(){
        if (lenguajes.isEmpty()){
            return "No se tiene informacion sobre los lenguajes";
        }
        return lenguajes.stream()
                .map(Libro::findLanguageName)
                .filter(Optional::isPresent) // Only keep matches
                .map(Optional::get)         // Extract the language name
                .collect(Collectors.joining(", "));
    }


    private static Optional<String> findLanguageName(String code) {
        return Lenguaje.CODIGOS_LENGUAJE.stream()
                .filter(entry -> entry.getKey().equals(code))
                .map(Map.Entry::getValue)
                .findFirst();
    }




    public String autoresToString(){
        StringBuilder stringAutores = new StringBuilder();
        int size = autores.size();
        Iterator<Autor> iterador = autores.iterator();
        int count = 0;

        if (size == 0){
            return "Desconocido";
        }

        while (iterador.hasNext()) {
            Autor autor = iterador.next();
            count++;

            if (size == 1){
                return autor.getNombre();
            }

            // Check for the last two elements
            if (count == size - 1) {
                //Tratar penultimo autor
                stringAutores.append(autor.getNombre());
            } else if (count == size) {
                //Tratar ultimo autor
                stringAutores.append(" y ").append(autor.getNombre());
            } else {
                //Tratar autores
                stringAutores.append(autor.getNombre()).append("; ");
            }
        }

        return stringAutores.toString();
    }

    public void setAutores(Set<Autor> autores) { this.autores = autores; for (Autor autor : autores) { autor.getLibros().add(this); } }

    public void addAutor(Autor autor){
        this.autores.add(autor);
    }

    public Set<Autor> getAutores() {
        return autores;
    }


    public long getId() {
        return id;
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
