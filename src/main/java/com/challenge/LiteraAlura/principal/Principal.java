package com.challenge.LiteraAlura.principal;

import com.challenge.LiteraAlura.model.Datos;
import com.challenge.LiteraAlura.model.DatosLibro;
import com.challenge.LiteraAlura.model.Libro;
import com.challenge.LiteraAlura.service.ConsumoAPI;
import com.challenge.LiteraAlura.service.ConvierteDatos;
import com.challenge.LiteraAlura.service.LibroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroService libroService;

    private final String azul = "\u001B[34m";
    private final String verde = "\u001B[32m";
    private final String rojo = "\u001B[31m";
    private final String colorReset = "\u001B[0m";

    @Autowired
    public Principal(LibroService libroService){
        this.libroService = libroService;
    }


    public void iniciarPrograma(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = String.format("""
                    ---------- MENU DE OPCIONES ----------
                    %s1 - Buscar Libro por nombre
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    %s              
                    0 - Salir %s
                    """, verde, rojo, colorReset);
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarTodosLosLibros();
                    break;
                case 3:
                    //codigo
                    break;
                case 4:
                    //codigo
                    break;
                case 5:
                    //codigo
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public void buscarLibroWeb(){
        System.out.println("---------- BUSQUEDA DE LIBROS ----------");
        System.out.println(azul + "Escriba palabras clave que quiera buscar, separando con espacios cada palabra");
        System.out.println(verde + "*Se mostraran los primeros 10 resultados*" + colorReset);

        var palabrasClave = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + palabrasClave.replace(" ", "%20"));
        //imprimirLindo(json);
        Datos datos = conversor.obtenerDatos(json, Datos.class);

        if (datos.datosLibro().size() > 0) {
            List<DatosLibro> opciones = datos.datosLibro().stream()
                    .limit(10)
                    .collect(Collectors.toList());

            AtomicInteger contador = new AtomicInteger(1);
            System.out.println("---------- RESULTADOS ----------");
            opciones.stream().forEach(s -> {
                int contadorActual = contador.getAndIncrement();
                System.out.println(azul + contadorActual + " - " + s.titulo());
                System.out.println(verde + "----------------- POR: " + s.autoresToString() + colorReset);
            });

            System.out.println(rojo + "\n0. SALIR" + colorReset);

            System.out.println("ELIJA UNA OPCION: ");
            int opcion = -1;
            while (true){
                opcion = teclado.nextInt();
                if (opcion > 0 && opcion <= opciones.size()){
                    opcion --;
                    DatosLibro libroElegido = opciones.get(opcion);
                    System.out.println("---------- SU ELECCION ----------");
                    System.out.println("EL LIBRO ELEGIDO FUE: ");
                    System.out.println(azul + "---> " + libroElegido.titulo());
                    System.out.println(verde + "----------------- POR: " + libroElegido.autoresToString() + colorReset);
                    System.out.println("---------- INFORME DE LA BASE DE DATOS ----------");
                    Libro libroAGuardar = new Libro(libroElegido);
                    libroService.guardarLibroConAutores(libroAGuardar, libroElegido.datosAutor());

                    return;
                } else if (opcion == 0) {
                    return;
                }
            }

        } else {
            System.out.println("---------- RESULTADOS ----------");
            System.out.println(rojo + "No se encontraron series con los términos dados" + colorReset);
        }


    }

    public void listarTodosLosLibros(){
        List<Libro> LibrosAMostrar = libroService.traerTodosLosLibros();
        LibrosAMostrar.forEach(System.out::println);

    }



    public static void imprimirLindo(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Parse the JSON string into a structured object (Map, List, etc.)
            Object jsonObject = mapper.readValue(json, Object.class);
            // Pretty-print the structured object
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            String prettyJson = writer.writeValueAsString(jsonObject);
            System.out.println(prettyJson);
        } catch (Exception e) {
            System.err.println("No se pudo imprimir lindamente el Json: " + e.getMessage());
        }
    }






}
