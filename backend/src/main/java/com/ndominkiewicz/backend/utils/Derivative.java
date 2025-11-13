package com.ndominkiewicz.backend.utils;

import java.util.function.BiFunction;

public class Derivative {
    private static final double h = 0.00001;
    public static double calculate(BiFunction<Double, Double, Double> function, double x, double y) {
        return (function.apply(x + h, y) - function.apply(x, y)) / h;
    }
}
