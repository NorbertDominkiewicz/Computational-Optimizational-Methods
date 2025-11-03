package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.model.FibonacciResult;
import com.ndominkiewicz.backend.model.MINMAX;
import com.ndominkiewicz.backend.utils.Point;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class FibonacciService {
    private double a, b, e;
    private int iterations;
    private int n;
    private double x1, x2;
    private MINMAX mode;
    private Function<Double, Double> function;
    private List<Point> points;
    public FibonacciResult solve(double a, double b, double e, String equation) {
        this.a = a;
        this.b = b;
        this.e = e;
        this.iterations = 0;
        this.mode = MINMAX.MAXIMUM;
        this.function = x -> {
            Expression expression = new ExpressionBuilder(equation)
                    .variable("x")
                    .build()
                    .setVariable("x", x);
            return expression.evaluate();
        };
        this.points = new ArrayList<>();
        this.initializeFunctionPoints(100);
        n = getN(1, 1, 1);
        x1 = getX1();
        x2 = getX2();
        return step3();
    }
    public void initializeFunctionPoints(int points) {
        double range = b - a;
        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = function.apply(x);
            this.points.add(new Point(x, y));
        }
    }
    private int getFn(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        int k = 2;
        int F_k1 = 1;
        int F_k2 = 1;
        int F_k = F_k1 + F_k2;
        if(k == n) return F_k;
        else return getFn(k + 1, n, F_k, F_k1);
    }
    private int getFn(int k, int n, int F_k1, int F_k2) {
        int F_k = F_k1 + F_k2;
        if(k == n) return F_k;
        else return getFn(k + 1, n, F_k, F_k1);
    }
    private int getN(int n, int F_k1, int F_k2) {
        int F_k = F_k1 + F_k2;
        double condition = (b - a) / F_k;
        if (condition < 2 * e) return n;
        else return getN(n + 1, F_k, F_k1);
    }
    private double getX1() {
        return b - ((double) getFn(this.n - 1) / getFn(this.n)) * (b - a);
    }
    private double getX2() {
        return a + ((double) getFn(this.n - 1) / getFn(this.n)) * (b - a);
    }
    private FibonacciResult step3() {
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
                n--;
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
                n--;
            }
        }
        return step4();
    }
    private FibonacciResult step4() {
        if ((Math.abs(x2 - x1) < e) || n == 1) {
            double result = (a + b) / 2;
            return new FibonacciResult("Maksimum", iterations, a, b, e, x1, x2, result, points);
        }
        iterations++;
        return step3();
    }
}
