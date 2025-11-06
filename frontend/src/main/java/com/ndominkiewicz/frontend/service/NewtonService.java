package com.ndominkiewicz.frontend.service;

import com.ndominkiewicz.frontend.result.NewtonResult;
import com.ndominkiewicz.frontend.utils.Parser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewtonService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public NewtonResult calculate() {
        try{
            String url = "http://localhost:8080/api/v1/newtonExample";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Parser.parseNewton(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public NewtonResult calculate(String equation) {
        return null;
    }

    public NewtonResult calculate(double a, double b, String equation) {
        return null;
    }

    public NewtonResult calculate(double a, double b, double e, String equation) {
        try{
            String url = "http://localhost:8080/api/v1/newton?a=-6&b=-1&e=0.01&firstDerivative=3*x%5E2-6*x-20&secondDerivative=6*x-6&thirdDerivative=6";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Parser.parseNewton(response.body());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
