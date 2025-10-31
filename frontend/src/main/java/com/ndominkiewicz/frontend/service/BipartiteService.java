package com.ndominkiewicz.frontend.service;

import com.ndominkiewicz.frontend.model.BipartiteResult;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BipartiteService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
//    public BipartiteResult calculate(double a, double b, double e, String equation) throws Exception{
//        String url = String.format("http://localhost:8080/api/bipartite?a=-6&b=-1&e=0.1&equation=x%5E3-3*x%5E2-20*x+1");
//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
//    }
    public String calculate(double a, double b, double e) {
        try{
            String url = String.format("http://localhost:8080/api/bipartite?a=-6&b=-1&e=0.1&equation=x%5E3-3*x%5E2-20*x+1");
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public String calculate() {
        try{
            String url = "http://localhost:8080/api/bipartite?a=-6&b=-1&e=0.1&equation=x%5E3-3*x%5E2-20*x+1";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
