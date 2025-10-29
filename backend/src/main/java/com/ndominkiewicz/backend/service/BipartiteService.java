package com.ndominkiewicz.backend.service;

import com.google.common.base.Function;
import com.ndominkiewicz.backend.model.BipartiteResult;
import org.springframework.stereotype.Service;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


@Service
public class BipartiteService {

    public static BipartiteResult solve(double a, double b, double e, String equation) {
        Function<Double, Double> function = getFunction(equation);
        double iterations = 1;
        return null;
    }

    private static void step1() {

    }

    private static void step2() {

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
