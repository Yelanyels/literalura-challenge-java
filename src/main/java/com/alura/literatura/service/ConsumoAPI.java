package com.alura.literatura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String obtenerDatos(String url) {
        // 1. Construyendo el Cliente (HttpClient)
        HttpClient client = HttpClient.newHttpClient();

        // 2. Construyendo la Solicitud (HttpRequest)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // 3. Gestionando la Respuesta (HttpResponse)
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al realizar la solicitud: " + e.getMessage());
        }

        return response.body(); // Devolvemos el JSON extraído
    }


}
