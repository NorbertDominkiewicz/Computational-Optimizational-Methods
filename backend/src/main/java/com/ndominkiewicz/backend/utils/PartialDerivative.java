package com.ndominkiewicz.backend.utils;

import com.ndominkiewicz.backend.model.DerivativeOrder;

import java.util.function.BiFunction;

public class PartialDerivative {
    private static final double h = 0.00001;

    public static double calculateX(BiFunction<Double, Double, Double> function, double x, double y, DerivativeOrder order) {
        try {
            switch (order) {
                case I -> {
                    return (function.apply(x + h, y) - function.apply(x, y)) / h;
                }
                case II -> {
                    return (function.apply(x + 2 * h, y) - 2 * function.apply(x + h, y) + function.apply(x, y)) / (h * h);
                }
            }
        } catch (ArithmeticException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public static double calculateY(BiFunction<Double, Double, Double> function, double x, double y, DerivativeOrder order) {
        try {
            switch (order) {
                case I -> {
                    return (function.apply(x, y + h) - function.apply(x, y)) / h;
                }
                case II -> {
                    return (function.apply(x, y + 2 * h) - 2 * function.apply(x, y + h) + function.apply(x, y)) / (h * h);
                }
            }
        } catch (ArithmeticException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public static double calculateXY(BiFunction<Double, Double, Double> function, double x, double y) {
        try {
            return (function.apply(x + h, y + h) - function.apply(x + h, y) - function.apply(x, y + h) + function.apply(x, y)) / (h * h);
        } catch (ArithmeticException e) {
            throw new RuntimeException(e);
        }
    }
}
