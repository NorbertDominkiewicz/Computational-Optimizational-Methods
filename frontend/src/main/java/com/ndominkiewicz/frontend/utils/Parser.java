package com.ndominkiewicz.frontend.utils;

import com.ndominkiewicz.frontend.model.BipartiteResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Parser {
    public static BipartiteResult parse(String content) {
        String [] split = content.split(",");
        String extreme = split[0].substring(9, split[1].length() -1);
        int iterations = Integer.parseInt(String.valueOf(split[1].charAt(13)));
        double a = Double.parseDouble(split[2].substring(5));
        double b = Double.parseDouble(split[3].substring(5));
        double L = Double.parseDouble(split[4].substring(5));
        double x1 = Double.parseDouble(split[5].substring(6));
        double x2 = Double.parseDouble(split[6].substring(6));
        double xsr = Double.parseDouble(split[7].substring(7, split[7].length() -1));
        return new BipartiteResult(extreme, iterations, a, b, L, x1, x2, xsr);
    }
}
