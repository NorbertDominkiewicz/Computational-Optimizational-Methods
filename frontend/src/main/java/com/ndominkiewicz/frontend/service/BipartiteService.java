package com.ndominkiewicz.frontend.service;

import com.ndominkiewicz.frontend.result.BipartiteResult;
import com.ndominkiewicz.frontend.result.NewtonResult;
import com.ndominkiewicz.frontend.utils.Parser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BipartiteService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public BipartiteResult calculate() {
        try{
            String url = "http://localhost:8080/api/v1/bipartiteExample";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Parser.parseBipartite(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BipartiteResult calculate(String equation) {
        return null;
    }

    public BipartiteResult calculate(double a, double b, String equation) {
        return null;
    }

    public BipartiteResult calculate(double a, double b, double e, String equation) {
        try{
            String url = "http://localhost:8080/api/v1/bipartite?a=-6&b=-1&e=0.1&equation=x%5E3-3*x%5E2-20*x+1";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Parser.parseBipartite(response.body());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

