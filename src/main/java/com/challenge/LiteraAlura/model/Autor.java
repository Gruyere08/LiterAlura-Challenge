package com.challenge.LiteraAlura.model;

import com.challenge.LiteraAlura.otros.Color;
import jakarta.persistence.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.EAGER, mappedBy = "autores")
    private Set<Libro> libros = new HashSet<>();

    private static int cantidadPopulares = 3;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.anio_nacimiento = datosAutor.anio_nacimiento();
        this.anio_fallecimiento = datosAutor.anio_fallecimiento();
    }

    @Override
    public String toString() {
        return "\n---------- AUTOR ---------- \n"
                + Color.CYAN + "Nombre: " + nombre + "\n"
                + Color.VERDE_CLARO + "Periodo: "
                + (anio_nacimiento != null ? anio_nacimiento : "Desconocido") + " - "
                + (anio_fallecimiento != null ? anio_fallecimiento : "Desconocido") + "\n"
                + Color.MORADO + "Libros asociados: " + topLibrosToString(cantidadPopulares) + Color.RESET;
    }


    public String topLibrosToString(int limite){
        List<String> listaPopulares = libros.stream()
                .sorted(Comparator.comparingLong(Libro::getDescargas).reversed())  // Sort by descargas in descending order
                .limit(libros.size())
                .map(Libro::getTitulo)  // Extract the "titulo" of each libro
                .toList();

        if (listaPopulares.isEmpty()){
            return "No hay libros registrados para este autor";
        }

        if(listaPopulares.size() == 1){
            return listaPopulares.get(0);
        }

        StringBuilder stringFinal = new StringBuilder();
        stringFinal.append(listaPopulares.get(0)).append("\n");

        for(String popular : listaPopulares.subList(1,listaPopulares.size() - 1)){
            stringFinal.append("----------------- ")
                    .append(popular)
                    .append("\n");
        }

        stringFinal.append("----------------- ").append(listaPopulares.get(listaPopulares.size()-1));

        return stringFinal.toString();
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void addLibro(Libro libro) { this.libros.add(libro); libro.addAutor(this); }


    public long getId() {
        return id;
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
