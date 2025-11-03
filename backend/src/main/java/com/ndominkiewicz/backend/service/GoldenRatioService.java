package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.model.GoldenRatioResult;
import com.ndominkiewicz.backend.model.MINMAX;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GoldenRatioService {
    private double a, b, e;
    private double k, x1, x2;
    private int iterations;
    private Function<Double, Double> function;
    private MINMAX mode;
    public GoldenRatioResult solve(double a, double b, double e, String equation) {
        this.a = a;
        this.b = b;
        this.e = e;
        this.k = getK();
        this.mode = MINMAX.MAXIMUM;
        this.function = getFunction(equation);
        return step1();
    }
    private GoldenRatioResult step1() {
        x1 = getX1();
        x2 = getX2();
        return step2();
    }
    private double getX1() {
        return x1 = b - k * (b - a);
    }
    private double getX2() {
        return x2 = a + k * (b - a);
    }
    private GoldenRatioResult step2() {
        switch (mode) {
            case MAXIMUM -> {
                if(function.apply(x1) > function.apply(x2)) {
                    b = x2;
                    x2 = x1;
                    x1 = getX1();
                } else {
                    a = x1;
                    x1 = x2;
                    x2 = getX2();
                }
            }
            case MINIMUM -> {
                if(function.apply(x1) < function.apply(x2)) {
                    b = x2;
                    x2 = x1;
                    x1 = getX1();
                } else {
                    a = x1;
                    x1 = x2;
                    x2 = getX2();
                }
            }
        }
        if(Math.abs(x2 - x1) < e) {
            return new GoldenRatioResult("Maksimum", a, b, e, (a + b) / 2, iterations);
        }
        iterations++;
        return step2();
    }
    private double getK() {
        return k = (Math.sqrt(5) - 1) / 2;
    }
    private Function<Double, Double> getFunction(String equation) {
        try {
            return x -> {
                Expression expression = new ExpressionBuilder(equation)
                        .variable("x")
                        .build()
                        .setVariable("x", x);
                return expression.evaluate();
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
