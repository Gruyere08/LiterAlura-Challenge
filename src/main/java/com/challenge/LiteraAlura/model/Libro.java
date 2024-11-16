package com.challenge.LiteraAlura.model;
import java.util.List;

public class Libro {

    private long id;
    private long id_guten;
    private String titulo;
    private List<DatosAutor> autores;
    private List<String> temas;
    private List<String> lenguajes;
    private long descargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro){}

}
