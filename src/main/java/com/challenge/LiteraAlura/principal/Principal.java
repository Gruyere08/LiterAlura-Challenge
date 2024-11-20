package com.challenge.LiteraAlura.principal;

import com.challenge.LiteraAlura.configuration.DynamicConfig;
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

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroService libroService;
    private int OpcionesPorPagina = Integer.parseInt(DynamicConfig.get("cantidad_opciones"));


    @Autowired
    public Principal(LibroService libroService){
        this.libroService = libroService;
    }


    public void iniciarPrograma(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = String.format("""
                    ---------- MENU DE PRINCIPAL ----------
                    %s1 - Buscar Libro por nombre
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    %s
                    9 - Configuraciones %s              
                    0 - Salir %s
                    """, Color.CYAN, Color.MORADO, Color.ROJO, Color.RESET);
            System.out.println(menu);
            opcion = solicitarEntero(0, 9);

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
                case 9:
                    mostrarMenuDeConfiguraciones();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println(Color.ROJO + "*El valor ingresado no es valido, por favor ingrese un valor valido*" + Color.RESET);
            }
        }
    }

    public void buscarLibroWeb(){
        // ZONA DE CONSULTA API
        System.out.println("---------- BUSQUEDA DE LIBROS ----------");
        System.out.println(Color.AZUL + "Escriba palabras clave que quiera buscar, separando con espacios cada palabra");
        System.out.println(Color.MORADO + "*Se mostraran los primeros " + OpcionesPorPagina +" resultados*" + Color.RESET);

        var palabrasClave = solicitarString();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + palabrasClave.replace(" ", "%20"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);


        //ZONA DE MANEJO DE PAGINAS

        if (!datos.datosLibro().isEmpty()) {
            List<List<DatosLibro>> paginas = dividirLista(datos.datosLibro(), OpcionesPorPagina);
            int indicePagina = 0;

            while (true){
                List<DatosLibro> opciones = paginas.get(indicePagina);
                AtomicInteger contador = new AtomicInteger(1 + (indicePagina* OpcionesPorPagina));
                System.out.println("---------- RESULTADOS ----------");
                opciones.stream().forEach(s -> {
                    int contadorActual = contador.getAndIncrement();
                    System.out.println(Color.AZUL + contadorActual + " - " + s.titulo());
                    System.out.println(Color.CYAN + "----------------- POR: " + s.autoresToString() + Color.RESET);
                });

                System.out.println("\n");
                System.out.println("PAGINA: " + (indicePagina + 1) +"/" + paginas.size());
                if (indicePagina != 0) {System.out.println(Color.VERDE_CLARO + (opciones.size() + 1) + ". Pagina ANTERIOR");}
                if (indicePagina != paginas.size() - 1) {System.out.println(Color.VERDE_CLARO + (opciones.size() + 2) +". Pagina SIGUIENTE");}
                System.out.println(Color.MORADO + (opciones.size() + 3) + ". Agregar todos los libros de esta pagina");
                System.out.println(Color.CYAN + (opciones.size() + 4) + ". Agregar TODOS los libros");
                System.out.println(Color.ROJO + "0. SALIR" + Color.RESET);

                System.out.println("ELIJA UNA OPCION: ");
                int opcion = -1;
                opcion = solicitarEntero(0, (opciones.size() + 4));

                if (opcion > 0 && opcion <= opciones.size()){
                    opcion --;
                    DatosLibro libroElegido = opciones.get(opcion);
                    System.out.println("---------- SU ELECCION ----------");
                    System.out.println(Color.MORADO + "EL LIBRO ELEGIDO FUE: ");
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
                    for(DatosLibro dato : opciones){
                        System.out.println("---------- SU ELECCION ----------");
                        System.out.println(Color.MORADO + "SE ELIGIERON LOS SIGUIENTES LIBROS: ");
                        System.out.println(Color.AZUL + "---> " + dato.titulo());
                        System.out.println(Color.CYAN + "----------------- POR: " + dato.autoresToString() + Color.RESET);
                    }


                    System.out.println("---------- INFORME DE LA BASE DE DATOS ----------");
                    List<Libro> librosAgregar = opciones.stream().map(Libro::new).toList();

                    for (int i = 0; i < librosAgregar.size(); i++) {
                        Libro libro = librosAgregar.get(i);
                        DatosLibro datosLibro = opciones.get(i);
                        libroService.guardarLibroConAutores(libro, datosLibro.datosAutor());
                    }

                    return;
                } else if (opcion == opciones.size() + 4) {
                    for(List<DatosLibro> listaInterior : paginas){
                        for (DatosLibro dato : listaInterior){
                            System.out.println("---------- SU ELECCION ----------");
                            System.out.println(Color.MORADO + "SE ELIGIERON LOS SIGUIENTES LIBROS: ");
                            System.out.println(Color.AZUL + "---> " + dato.titulo());
                            System.out.println(Color.CYAN + "----------------- POR: " + dato.autoresToString() + Color.RESET);
                        }

                    }

                    System.out.println("---------- INFORME DE LA BASE DE DATOS ----------");

                    List<DatosLibro> datosLibrosAgregar = paginas.stream()
                            .flatMap(List::stream)
                            .toList();

                    List<Libro> librosAgregar = datosLibrosAgregar.stream()
                            .map(Libro::new)
                            .toList();

                    for (int i = 0; i < librosAgregar.size(); i++) {
                        Libro libro = librosAgregar.get(i);
                        DatosLibro datosLibro = datosLibrosAgregar.get(i);
                        libroService.guardarLibroConAutores(libro, datosLibro.datosAutor());
                    }
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


    public void mostrarMenuDeConfiguraciones(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = String.format("""
                    ---------- MENU DE OPCIONES ----------
                    %s1 - Ocultar Libros sin Autores(%s)
                    2 - Ocultar Autores sin Libros(%s)
                    3 - Numero de elementos por pagina en busquedas(%s)
                    4 - Eliminar Libros
                    5 - Eliminar autores
                    6 - Limpiar Base de Datos
                    %s
                    0 - Salir %s
                    """,
                    Color.MORADO,
                    (LibroService.isOcultarLibrosSinAutores() ? "SI" : "NO"),
                    (LibroService.isOcultarAutoresSinLibros() ? "SI" : "NO"),
                    OpcionesPorPagina,
                    Color.ROJO,
                    Color.RESET);
            System.out.println(menu);
            opcion = solicitarEntero(0, 9);

            switch (opcion) {
                case 1:
                    LibroService.toggleOcultarLibrosSinAutores();
                    break;
                case 2:
                    LibroService.toggleOcultarAutoresSinLibros();
                    break;
                case 3:
                    cambiarCantidadDeElementosPorPagina();
                    break;
                case 4:
                    libroService.borrarTodosLosLibros();
                    break;
                case 5:
                    libroService.borrarTodosLosaAutores();
                    break;
                case 6:
                    libroService.borrarTodosLosaAutores();
                    libroService.borrarTodosLosLibros();
                    break;
                case 0:
                    break;
                default:
                    System.out.println(Color.ROJO + "*El valor ingresado no es valido, por favor ingrese un valor valido*" + Color.RESET);
            }
        }

    }


    public void cambiarCantidadDeElementosPorPagina(){
        System.out.println("---------- CONFIGURACION DE ELEMENTOS POR PAGINA ----------");
        System.out.println(Color.AZUL + "Indique el numero de elementos que desea ver por pagina durante las busquedas");
        System.out.println(Color.MORADO + "*La configuracion actual es de " + OpcionesPorPagina +" elementos por pagina*" + Color.RESET);
        int cantidad = this.solicitarEntero();

        OpcionesPorPagina = cantidad;
        DynamicConfig.set("cantidad_opciones", Integer.toString(cantidad));
        DynamicConfig.save();
        System.out.println(Color.MORADO + "Se cambió el numero de elementos por pagina a: " + cantidad + Color.RESET);
    }


    public int solicitarEntero(){
        int entero;
        while (true){
            try{
                entero = teclado.nextInt();
                teclado.nextLine();
                break;
            }catch (InputMismatchException e){
                System.out.println(Color.ROJO + "*El valor ingresado no es valido, por favor ingrese un valor valido*" + Color.RESET);
                teclado.nextLine();
            }
        }
        return entero;
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


    public static <T> List<List<T>> dividirLista(List<T> lista, int tamanoGrupo) {
        List<List<T>> listasParticionadas = new ArrayList<>();
        for (int i = 0; i < lista.size(); i += tamanoGrupo) {
            // Create a new ArrayList to ensure the sublists are independent of the original list
            listasParticionadas.add(new ArrayList<>(lista.subList(i, Math.min(i + tamanoGrupo, lista.size()))));
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
