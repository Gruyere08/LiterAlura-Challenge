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
}
