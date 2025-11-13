package com.ndominkiewicz.frontend.service;

import com.ndominkiewicz.frontend.result.GoldenRatioResult;
import com.ndominkiewicz.frontend.result.NewtonResult;
import com.ndominkiewicz.frontend.result.SecantResult;
import com.ndominkiewicz.frontend.utils.Parser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GoldenRatioService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public GoldenRatioResult calculate() {
        try{
            String url = "http://localhost:8080/api/v1/goldenratioExample";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Parser.parseGoldenRatio(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public GoldenRatioResult calculate(String equation) {
        return null;
    }

    public GoldenRatioResult calculate(double a, double b, String equation) {
        return null;
    }

    public GoldenRatioResult calculate(double a, double b, double e, String equation) {
        try{
            String url = "http://localhost:8080/api/v1/goldenratio?a=-6&b=-1&e=0.1&equation=x%5E3-3*x%5E2-20*x+1";
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Parser.parseGoldenRatio(response.body());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}