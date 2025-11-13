package com.ndominkiewicz.frontend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndominkiewicz.frontend.result.*;

public class Parser {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static BipartiteResult parseBipartite(String content) {
        try {
            return mapper.readValue(content, BipartiteResult.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
    public static SecantResult parseSecant(String content) {
        try {
            return mapper.readValue(content, SecantResult.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
    public static GoldenRatioResult parseGoldenRatio(String content) {
        try {
            return mapper.readValue(content, GoldenRatioResult.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
    public static BisectionResult parseBisection(String content) {
        try {
            return mapper.readValue(content, BisectionResult.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
    public static FibonacciResult parseFibonacci(String content) {
        try {
            return mapper.readValue(content, FibonacciResult.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
    public static NewtonResult parseNewton(String content) {
        try {
            return mapper.readValue(content, NewtonResult.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
