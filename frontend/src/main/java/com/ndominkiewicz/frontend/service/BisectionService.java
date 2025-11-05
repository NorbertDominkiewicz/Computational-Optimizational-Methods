package com.ndominkiewicz.frontend.service;

import com.ndominkiewicz.frontend.model.BisectionResult;
import com.ndominkiewicz.frontend.model.NewtonResult;
import com.ndominkiewicz.frontend.utils.Parser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BisectionService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public BisectionResult calculate() {
        try{
            String url = "http://localhost:8080/api/v1/bisection?a=-6&b=-1&e=0.01&equation=3*x%5E2-6*x-20";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Parser.parseBisection(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
