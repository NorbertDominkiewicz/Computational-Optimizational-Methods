package com.ndominkiewicz.frontend.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BisectionService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public String calculate() {
        try{
            String url = "http://localhost:8080/api/bisectionTest";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
