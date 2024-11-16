package com.challenge.LiteraAlura.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
    @JsonAlias("id") long id_guten,
    @JsonAlias("title") String titulo,
    @JsonAlias("authors") List<DatosAutor> datosAutor,
    @JsonAlias("subjects") List<String> temas,
    @JsonAlias("languages") List<String> lenguajes,
    @JsonAlias("download_count") long descargas
) {

    public String autoresToString(){
        if (datosAutor.size() == 1) {
            return datosAutor.get(0).nombre();
        } else if (datosAutor.size() > 1) {
            String string = "";
            for (int i = 0; i < datosAutor.size() - 2; i++) {
                string = string + datosAutor.get(i).nombre() + "; ";
            }
            string = string + datosAutor.get(datosAutor.size()-2).nombre();
            string = string + " y " + datosAutor.get(datosAutor.size()-1).nombre();
            return string;
        } else {
            return "Autor Desconocido";
        }
    }

}
