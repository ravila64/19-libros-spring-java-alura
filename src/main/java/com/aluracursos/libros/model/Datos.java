package com.aluracursos.libros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// listaLibros es resultados de las variables del curso
@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
      @JsonAlias("results") List<DatosLibros> listaLibros
) {
}
