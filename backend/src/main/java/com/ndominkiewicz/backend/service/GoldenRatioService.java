package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.result.GoldenRatioResult;
import com.ndominkiewicz.backend.model.MINMAX;
import com.ndominkiewicz.backend.utils.Point;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class GoldenRatioService {
    private double a, b, e;
    private double k, x1, x2;
    private int iterations;
    private Function<Double, Double> function;
    private MINMAX mode;
    private final List<Point> points = new ArrayList<>();
    public GoldenRatioResult solve(double a, double b, double e, String equation) {
        this.clearData();
        this.a = a;
        this.b = b;
        this.e = e;
        this.k = getK();
        this.mode = MINMAX.MAXIMUM;
        this.function = getFunction(equation);
        return step1();
    }
    public GoldenRatioResult solve() {
        clearData();
        a = -6;
        b = -1;
        e = 0.001;
        k = getK();
        mode = MINMAX.MAXIMUM;
        function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;
        initializeFunctionPoints(500);
        return step1();
    }
    private void clearData() {
        a = 0;
        b = 0;
        e = 0;
        x1 = 0;
        x2 = 0;
        iterations = 0;
        points.clear();
    }
    public void initializeFunctionPoints(int points) {
        double range = b - a;
        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = function.apply(x);
            this.points.add(new Point(x, y));
        }
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
            double result = (a + b) / 2;
            return new GoldenRatioResult("Maksimum", e, result, function.apply(result), iterations, points);
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
