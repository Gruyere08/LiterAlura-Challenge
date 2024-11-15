package com.challenge.LiteraAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") int anio_nacimiento,
        @JsonAlias("death_year") int anio_fallecimiento
) {
}
