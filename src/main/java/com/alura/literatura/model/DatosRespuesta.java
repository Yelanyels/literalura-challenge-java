package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuesta(
        // El nombre aquí debe ser "resultadoLibros" para que el main lo encuentre
        @JsonAlias("results") List<DatosLibro> resultadoLibros
) {
}