package com.ndominkiewicz.backend.service;
import com.ndominkiewicz.backend.result.BipartiteResult;
import com.ndominkiewicz.backend.model.MINMAX;
import com.ndominkiewicz.backend.utils.Point;
import org.springframework.stereotype.Service;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class BipartiteService {
    public double a, b;
    public double L;
    public double x1, x2;
    public double xsr;
    public double e;
    public int iterations;
    public MINMAX mode;
    public double [] points;
    public Function<Double, Double> function;
    public List<Point> functionPoints = new ArrayList<>();
    public BipartiteResult solve(double a, double b, double e, String equation) {
        clearData();
        this.a = a;
        this.b = b;
        this.e = e;
        this.mode = MINMAX.MAXIMUM;
        this.function = x -> {
            Expression expression = new ExpressionBuilder(equation)
                    .variable("x").build().setVariable("x", x);
            return expression.evaluate();
    };
        this.iterations = 1;
        this.initializeFunctionPoints(500);
        this.step1();
        return step2();
    }
    public BipartiteResult solve() {
        clearData();
        a = -6;
        b = -1;
        e = 0.001;
        mode = MINMAX.MAXIMUM;
        function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;
        iterations = 1;
        initializeFunctionPoints(500);
        step1();
        return step2();
    }
    private void clearData() {
        a = 0;
        b = 0;
        e = 0;
        L = 0;
        x1 = 0;
        x2 = 0;
        iterations = 0;
        functionPoints.clear();
    }
    public void initializeFunctionPoints(int points) {
        double range = b - a;
        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = function.apply(x);
            functionPoints.add(new Point(x, y));
        }
    }
    private void step1() {
        xsr = (a + b) / 2;
    }

    private BipartiteResult step2() {
        L = b - a;
        x1 = a + (L / 4);
        x2 = b - (L / 4);
        return step3();
    }

    private BipartiteResult step3() {
        switch (mode) {
            case MINIMUM -> {
                if(function.apply(x1) < function.apply(xsr)) {
                    b = xsr;
                    xsr = x1;
                    return step5();
                } else {
                    return step4();
                }
            }
            case MAXIMUM -> {
                if(function.apply(x1) > function.apply(xsr)) {
                    b = xsr;
                    xsr = x1;
                    return step5();
                } else {
                    return step4();
                }
            }
        }
        return null;
    }

    private BipartiteResult step4() {
        switch (mode) {
            case MINIMUM -> {
                if(function.apply(x2) < function.apply(xsr)) {
                    a = xsr;
                    xsr = x2;
                    return step5();
                } else {
                    a = x1;
                    b = x2;
                    return step5();
                }
            }
            case MAXIMUM -> {
                if(function.apply(x2) > function.apply(xsr)) {
                    a = xsr;
                    xsr = x2;
                    return step5();
                } else {
                    a = x1;
                    b = x2;
                    return step5();
                }
            }
        }
        return null;
    }

    private BipartiteResult step5() {
        if (L <= e) {
            return new BipartiteResult("Maksimum", iterations, e, xsr, function.apply(xsr),functionPoints);
        } else {
            iterations++;
            return step2();
        }
    }
}
