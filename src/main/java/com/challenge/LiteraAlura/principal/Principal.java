package com.challenge.LiteraAlura.principal;

import com.challenge.LiteraAlura.model.Autor;
import com.challenge.LiteraAlura.model.Datos;
import com.challenge.LiteraAlura.model.DatosLibro;
import com.challenge.LiteraAlura.model.Libro;
import com.challenge.LiteraAlura.otros.Color;
import com.challenge.LiteraAlura.service.ConsumoAPI;
import com.challenge.LiteraAlura.service.ConvierteDatos;
import com.challenge.LiteraAlura.service.LibroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
    private final int OPCIONES_POR_PAGINA = 5;


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
                    """, Color.AZUL, Color.ROJO, Color.RESET);
            System.out.println(menu);
            opcion = solicitarEntero(0, 5);

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarTodosLosLibros();
                    break;
                case 3:
                    listarTodosLosAutores();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
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
        // ZONA DE CONSULTA API
        System.out.println("---------- BUSQUEDA DE LIBROS ----------");
        System.out.println(Color.AZUL + "Escriba palabras clave que quiera buscar, separando con espacios cada palabra");
        System.out.println(Color.MORADO + "*Se mostraran los primeros " + OPCIONES_POR_PAGINA +" resultados*" + Color.RESET);

        var palabrasClave = solicitarString();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + palabrasClave.replace(" ", "%20"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);




        //ZONA DE MANEJO DE PAGINAS

        if (!datos.datosLibro().isEmpty()) {
            List<List<DatosLibro>> paginas = dividirLista(datos.datosLibro(), OPCIONES_POR_PAGINA);
            int indicePagina = 0;




            while (true){
                List<DatosLibro> opciones = paginas.get(indicePagina);
                AtomicInteger contador = new AtomicInteger(1 + (indicePagina*OPCIONES_POR_PAGINA));
                System.out.println("---------- RESULTADOS ----------");
                opciones.stream().forEach(s -> {
                    int contadorActual = contador.getAndIncrement();
                    System.out.println(Color.AZUL + contadorActual + " - " + s.titulo());
                    System.out.println(Color.CYAN + "----------------- POR: " + s.autoresToString() + Color.RESET);
                });

                System.out.println("\n");
                System.out.println("PAGINA: " + (indicePagina + 1) +"/" + paginas.size());
                if (indicePagina != 0) {System.out.println(Color.MORADO + (opciones.size() + 1) + ". PAGINA ANTERIOR");}
                if (indicePagina != paginas.size() - 1) {System.out.println(Color.MORADO + (opciones.size() + 2) +". PAGINA SIGUIENTE");}
                System.out.println(Color.MORADO + (opciones.size() + 3) + ". AGREGAR TODOS");
                System.out.println(Color.ROJO + "0. SALIR" + Color.RESET);

                System.out.println("ELIJA UNA OPCION: ");
                int opcion = -1;
                opcion = solicitarEntero(0, (opciones.size() + 3));

                if (opcion > 0 && opcion <= opciones.size()){
                    opcion --;
                    DatosLibro libroElegido = opciones.get(opcion);
                    System.out.println("---------- SU ELECCION ----------");
                    System.out.println("EL LIBRO ELEGIDO FUE: ");
                    System.out.println(Color.AZUL + "---> " + libroElegido.titulo());
                    System.out.println(Color.CYAN + "----------------- POR: " + libroElegido.autoresToString() + Color.RESET);
                    System.out.println("---------- INFORME DE LA BASE DE DATOS ----------");
                    Libro libroAGuardar = new Libro(libroElegido);
                    libroService.guardarLibroConAutores(libroAGuardar, libroElegido.datosAutor());

                    return;
                } else if (opcion == 0) {
                    return;
                } else if (opcion == opciones.size() + 1) {
                    //Volver a pagina anterior
                    if (indicePagina == 0){
                        System.out.println(Color.ROJO + "*El valor ingresado no es valido, por favor ingrese un valor valido*" + Color.RESET);
                        continue;
                    } else {
                        indicePagina --;
                        continue;
                    }
                } else if (opcion == opciones.size() + 2) {
                    if (indicePagina == paginas.size() -1){
                        System.out.println(Color.ROJO + "*El valor ingresado no es valido, por favor ingrese un valor valido*" + Color.RESET);
                        continue;
                    } else {
                        indicePagina ++;
                        continue;
                    }
                } else if (opcion == opciones.size() + 3) {
                    System.out.println("---------- INFORME DE LA BASE DE DATOS ----------");
                    List<Libro> librosAgregar = opciones.stream().map(Libro::new).toList();
                    librosAgregar.forEach(l -> libroService.guardarLibroConAutores(l,opciones.get(librosAgregar.indexOf(l)).datosAutor()));
                    return;
                }
            }

        } else {
            System.out.println("---------- RESULTADOS ----------");
            System.out.println(Color.ROJO + "No se encontraron series con los términos dados" + Color.RESET);
        }


    }

    public void listarTodosLosLibros(){
        List<Libro> librosAMostrar = libroService.traerTodosLosLibros();
        librosAMostrar.forEach(System.out::println);

    }

    public void listarTodosLosAutores(){
        List<Autor> autoresAMostrar = libroService.traerTodosLosautores();
        autoresAMostrar.forEach(System.out::println);

    }


    public void listarAutoresVivosEnAnio(){
        System.out.println("---------- BUSQUEDA DE AUTORES POR AÑO ----------");
        System.out.println(Color.AZUL + "Ingrese el año que desea buscar");
        System.out.println(Color.MORADO + "*Se mostraran los autores que estaban vivos en ese año*" + Color.RESET);
        int anio = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autoresAMostrar = libroService.traerAutoresVivosEnAnio(anio);
        System.out.println("---------- AUTORES VIVOS EN EL AÑO " + anio + " ----------");
        autoresAMostrar.forEach(System.out::println);
    }

    public int solicitarEntero(int limiteInferior, int limiteSuperior){
        int entero;
        while (true){
            try{
                entero = teclado.nextInt();
                teclado.nextLine();
                if (entero >= limiteInferior && entero <= limiteSuperior){
                    break;
                }
                System.out.println(Color.ROJO + "*El valor ingresado no es valido, por favor ingrese un valor valido*" + Color.RESET);
            }catch (InputMismatchException e){
                System.out.println(Color.ROJO + "*El valor ingresado no es valido, por favor ingrese un valor valido*" + Color.RESET);
                teclado.nextLine();
            }
        }
        return entero;
    }


    public String solicitarString() {
        String input;
        while (true) {
            input = teclado.nextLine(); // Read input
            if (input.trim().isEmpty()) {
                System.out.println(Color.ROJO + "*El valor ingresado no puede estar vacío." + Color.RESET);
            } else {
                return input;  // Return the valid string
            }
        }
    }


    public static List<List<DatosLibro>> dividirLista(List<DatosLibro> lista, int tamanoGrupo) {
        List<List<DatosLibro>> listasParticionadas = new ArrayList<>();
        for (int i = 0; i < lista.size(); i += tamanoGrupo) {
            listasParticionadas.add(lista.subList(i, Math.min(i + tamanoGrupo, lista.size())));
        }
        return listasParticionadas;
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
