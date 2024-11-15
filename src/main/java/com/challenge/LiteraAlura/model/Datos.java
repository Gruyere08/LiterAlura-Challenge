package com.challenge.LiteraAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(@JsonAlias("results") DatosLibro datosLibro) {
}
