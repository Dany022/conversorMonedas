package com.conversormonedas;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaMoneda {
    public Moneda buscaMoneda(String monedaBase) {
        String apiKey = "35d6db294cf89e9fff006ea4"; // Tu key real
        String direccion = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaBase;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            // Debug desactivado para salida más limpia

            Gson gson = new Gson();
            Moneda moneda = gson.fromJson(json, Moneda.class);
            return moneda;

        } catch (Exception e) {
            System.out.println("Error en la consulta: " + e.getMessage());
            return null;
        }
    }
}
