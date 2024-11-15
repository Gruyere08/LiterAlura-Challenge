package com.challenge.LiteraAlura.principal;

import com.challenge.LiteraAlura.model.Datos;
import com.challenge.LiteraAlura.service.ConsumoAPI;
import com.challenge.LiteraAlura.service.ConvierteDatos;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books";
    private ConvierteDatos conversor = new ConvierteDatos();


    public void iniciarPrograma(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar Libro por nombre
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por titulo
                    5 - Top 5 mejores series
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    //codigo
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
        System.out.println("Escriba palabras clave que quiera buscar, separando con espacios cada palabra");
        var palabrasClave = teclado.nextLine();
        System.out.println(URL_BASE + "?search=" + palabrasClave.replace(" ", "%20"));
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + palabrasClave.replace(" ", "%20"));
        //imprimirLindo(json);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);
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
            System.err.println("Failed to parse and pretty-print JSON: " + e.getMessage());
        }
    }






}