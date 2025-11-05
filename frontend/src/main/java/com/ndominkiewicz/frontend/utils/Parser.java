package com.ndominkiewicz.frontend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndominkiewicz.frontend.model.BipartiteResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ndominkiewicz.frontend.model.NewtonResult;

public class Parser {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static BipartiteResult parseBipartite(String content) {
        try {
            return mapper.readValue(content, BipartiteResult.class);
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
