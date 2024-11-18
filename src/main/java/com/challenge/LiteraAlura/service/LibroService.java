package com.challenge.LiteraAlura.service;

import com.challenge.LiteraAlura.model.Autor;
import com.challenge.LiteraAlura.model.DatosAutor;
import com.challenge.LiteraAlura.model.Libro;
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

    @Autowired
    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


    public void guardarLibroConAutores(Libro libro, List<DatosAutor> datosAutor){

        //Buscamos si el libro existe
        Optional<Libro> libroExistente = libroRepository.findByTitulo(libro.getTitulo());
        if (libroExistente.isPresent()){
            System.out.println("El libro elegido ya existe en la base de datos");
            return;
        }

        System.out.println("ID ANTES DE INSERTAR: "+ libro.getId());
        libroRepository.save(libro);
        libro = libroRepository.findByTitulo(libro.getTitulo()).get();
        System.out.println("ID DESPUES DE INSERTAR: "+ libro.getId());


        //procesamos los autores
        Set<Autor> autoresParaAgregar = new HashSet<>();
        for(DatosAutor datoAutor : datosAutor){
            Optional<Autor> autorExistente = autorRepository.findByNombre(datoAutor.nombre());
            Autor autor;

            if(autorExistente.isPresent()){
                autor = autorExistente.get();
                System.out.println("AUTOR YA EXISTE EN LA BASE DE DATOS: " + autor.getNombre());
            }else {
                autor = new Autor(datoAutor);
                System.out.println("NUEVO AUTOR REGISTRADO: " + autor.getNombre());
                autorRepository.save(autor);
                autor = autorRepository.findByNombre(autor.getNombre()).get();

            }
            autor.addLibro(libro);
            autoresParaAgregar.add(autor);
        }

        libro.setAutores(autoresParaAgregar);
        libroRepository.save(libro);
        System.out.println("NUEVO LIBRO REGISTRADO: " + libro.getTitulo());
        return;
    }


    public List<Libro> traerTodosLosLibros(){
        return libroRepository.findAll();
    }

    @Transactional
    public List<Autor> traerTodosLosautores(){
        return autorRepository.findAllWithLibros();
    }

    public List<Autor> traerAutoresVivosEnAnio(int anio){
        return autorRepository.findAutoresVivosEnAnio(anio);
    }


}
