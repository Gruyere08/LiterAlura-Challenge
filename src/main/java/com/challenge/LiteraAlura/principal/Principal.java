package com.challenge.LiteraAlura.principal;

import com.challenge.LiteraAlura.service.ConsumoAPI;

import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();


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
                    //codigo
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








}
