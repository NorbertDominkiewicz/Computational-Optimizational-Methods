package com.ndominkiewicz.backend.service;

import com.google.common.base.Function;
import com.ndominkiewicz.backend.model.BipartiteResult;
import com.ndominkiewicz.backend.model.MINMAX;

import org.springframework.stereotype.Service;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


@Service
public class BipartiteService {

    public static BipartiteResult solve(double a, double b, double e, String equation) {
        Function<Double, Double> function = getFunction(equation);
        MINMAX mode = evaluateMode(equation);
        return BipartiteService.solve(a, b, e, function, mode);
    }

    public static BipartiteResult solve(double a, double b, double e, Function<Double, Double> function, MINMAX mode) {
        step2(a, b, step1(a, b), function, mode);
        return null;
    }

    private static double step1(double a, double b) {
        return (a + b) / 2;
    }

    private static void step2(double a, double b, double xsr, Function<Double, Double> function, MINMAX mode) {
        double L = b - a;
        double x1 = a + (L / 4);
        double x2 = b - (L / 4);
        step3(a, b, xsr, L, x1, x2, function, mode);
    }

    private static void step3(double a, double b, double xsr, double L, double x1, double x2, Function<Double, Double> function, MINMAX mode) {
        switch (mode) {
            case MINIMUM -> {
                if(function.apply(x1) < function.apply(xsr)) {
                    b = xsr;
                    xsr = x1;
                    step5();
                } 
            }
        }
    }

    private static MINMAX evaluateMode(String equation) {
        /* to be made */
        return MINMAX.MAXIMUM;
    }

    private static Function<Double, Double> getFunction(String equation) {
        if (equation == null || equation.trim().isEmpty()) {
            throw new IllegalArgumentException("Equation cannot be null or empty");
        }
        return x -> {
            try {
                Expression expression = new ExpressionBuilder(equation)
                        .variables("x", "X")
                        .build()
                        .setVariable("x", x)
                        .setVariable("X", x);

                double result = expression.evaluate();

                if (Double.isNaN(result) || Double.isInfinite(result)) {
                    throw new ArithmeticException("Invalid result: " + result);
                }
                return result;
            } catch (Exception e) {
                throw new RuntimeException("Failed to evaluate equation: " + equation + " for x=" + x, e);
            }
        };
    }
}
