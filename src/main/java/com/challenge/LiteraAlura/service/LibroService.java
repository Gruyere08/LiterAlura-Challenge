package com.challenge.LiteraAlura.service;

import com.challenge.LiteraAlura.configuration.DynamicConfig;
import com.challenge.LiteraAlura.model.Autor;
import com.challenge.LiteraAlura.model.DatosAutor;
import com.challenge.LiteraAlura.model.DatosLibro;
import com.challenge.LiteraAlura.model.Libro;
import com.challenge.LiteraAlura.otros.Color;
import com.challenge.LiteraAlura.repository.AutorRepository;
import com.challenge.LiteraAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LibroService {

    private LibroRepository libroRepository;

    private AutorRepository autorRepository;

    private static boolean ocultarAutoresSinLibros = Boolean.parseBoolean(DynamicConfig.get("ocultarAutoresSinLibros"));

    private static boolean ocultarLibrosSinAutores = Boolean.parseBoolean(DynamicConfig.get("ocultarLibrosSinAutores"));

    @Autowired
    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


    @Transactional
    public void guardarLibroConAutores(DatosLibro datoLibro) {

        // Check if the book already exists
        Optional<Libro> libroExistente = libroRepository.findByTitulo(datoLibro.titulo());
        if (libroExistente.isPresent()) {
            System.out.println("El libro \"" + libroExistente.get().getTitulo() + "\" ya existe en la base de datos");
            return;
        }

        // Create and save the book
        Libro libro = new Libro(datoLibro);
        libroRepository.save(libro);

        for (DatosAutor datoAutor : datoLibro.datosAutor()) {
            Autor autor;

            // Check if the author already exists
            Optional<Autor> autorExistente = autorRepository.findByNombre(datoAutor.nombre());
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
                System.out.println("AUTOR YA EXISTE EN LA BASE DE DATOS: " + autor.getNombre());
            } else {
                autor = new Autor(datoAutor);
                System.out.println("NUEVO AUTOR REGISTRADO: " + autor.getNombre());
                autorRepository.save(autor);
            }

            // Link the book to the author
            autor.addLibro(libro);
        }

        // Update the authors of the book
        libroRepository.saveAndFlush(libro);

        System.out.println("NUEVO LIBRO REGISTRADO: " + libro.getTitulo());
    }


    public List<Libro> traerTodosLosLibros(){
        if(ocultarLibrosSinAutores){
            return libroRepository.findAllWithAutores();
        } else {
            return libroRepository.findAll();
        }
    }

    @Transactional
    public List<Autor> traerTodosLosautores(){
        if (ocultarAutoresSinLibros){
            return autorRepository.findAllWithLibros();
        }else {
            return autorRepository.findAll();
        }
    }

    public void borrarLibros(List<Libro> libros){
        for (Libro libro : libros) {
            libro.getAutores().forEach(autor -> autor.getLibros().remove(libro));
            autorRepository.saveAll(libro.getAutores());
            libro.getAutores().clear();

            libroRepository.saveAndFlush(libro);

            libroRepository.delete(libro);
        }
        System.out.println("---------- INFORME DE LA BASE DE DATOS ----------");
        System.out.println(Color.CYAN + "Se borraron los siguientes libros: " + Color.MORADO);
        libros.forEach(l -> System.out.println(l.getTitulo()));
        System.out.println(Color.RESET);
    }

    public void borrarAutores(List<Autor> autores){
        for (Autor autor : autores) {
            autor.getLibros().forEach(libro -> libro.getAutores().remove(autor));
            libroRepository.saveAll(autor.getLibros());
            autor.getLibros().clear();
            System.out.println("id antes de save: " + autor.getId());


            autorRepository.saveAndFlush(autor);

            System.out.println("id despues de save " + autor.getId());

            autorRepository.delete(autor);
        }
        System.out.println("---------- INFORME DE LA BASE DE DATOS ----------");
        System.out.println(Color.CYAN + "Se borraron los siguientes autores: " + Color.MORADO);
        autores.forEach(a -> System.out.println(a.getNombre()));
        System.out.println(Color.RESET);
    }

    public void borrarTodosLosLibros(){
        libroRepository.deleteAllLibros();
    }


    public void borrarTodosLosAutores(){
        autorRepository.deleteAllAutores();
    }


    public List<Autor> traerAutoresVivosEnAnio(int anio){
        return autorRepository.findAutoresVivosEnAnio(anio);
    }


    public List<Libro> traerLibrosPorLenguaje(String lenguaje){
        return libroRepository.findByLenguaje(lenguaje);
    }

    public static boolean isOcultarAutoresSinLibros() {
        return ocultarAutoresSinLibros;
    }

    public static void toggleOcultarAutoresSinLibros() {
        ocultarAutoresSinLibros = !ocultarAutoresSinLibros ;
        DynamicConfig.set("ocultarAutoresSinLibros",Boolean.toString(ocultarAutoresSinLibros));
        DynamicConfig.save();
    }

    public static boolean isOcultarLibrosSinAutores() {
        return ocultarLibrosSinAutores;
    }

    public static void toggleOcultarLibrosSinAutores() {
        ocultarLibrosSinAutores = !ocultarLibrosSinAutores ;
        DynamicConfig.set("ocultarLibrosSinAutores",Boolean.toString(ocultarLibrosSinAutores));
        DynamicConfig.save();
    }
}
