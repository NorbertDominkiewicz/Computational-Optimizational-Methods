package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.model.NewtonResult;
import com.ndominkiewicz.backend.model.SecantResult;
import com.ndominkiewicz.backend.utils.Point;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

enum Motionless {
    A, B
}

@Service
public class SecantService {
    private double a, b, e;
    private double xn, xn1;
    private int iterations;
    private Function<Double, Double> function;
    private Function<Double, Double> firstDerivative;
    private Function<Double, Double> thirdDerivative;
    private List<Point> points = new ArrayList<>();
    public SecantResult solve() {
        a = -6;
        b = 1;
        e = 0.001;
        function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;
        firstDerivative = x -> 3 * Math.pow(x, 2) - 6 * x - 20;
        thirdDerivative = x -> 6.0;
        initializeFunctionPoints(500);
        return canDo() ? getXn() : null;
    }
    public SecantResult solve(double a, double b, double e, String firstDerivative, String thirdDerivative) {
        this.clearData();
        this.a = a;
        this.b = b;
        this.e = e;
        this.firstDerivative = getFunction(firstDerivative);
        this.thirdDerivative = getFunction(thirdDerivative);
        return canDo() ? getXn() : null;
    }
    public void initializeFunctionPoints(int points) {
        double range = b - a;
        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = function.apply(x);
            this.points.add(new Point(x, y));
        }
    }
    private void clearData() {
        a = 0;
        b = 0;
        e = 0;
        xn = 0;
        xn1 = 0;
        iterations = 0;
    }
    private SecantResult getXn() {
        Motionless letter;
        if (thirdDerivative.apply(a) * firstDerivative.apply(a) > 0) {
            xn = b;
            letter = Motionless.A;
        } else {
            xn = a;
            letter = Motionless.B;
        }
        return getXn1(letter);
    }
    private SecantResult getXn1(Motionless letter) {
        switch (letter) {
            case A -> xn1 = xn - (firstDerivative.apply(xn) / (firstDerivative.apply(xn) - firstDerivative.apply(a))) * (xn - a);
            case B -> xn1 = xn - (firstDerivative.apply(xn) / (firstDerivative.apply(xn) - firstDerivative.apply(b))) * (b - xn);
        }
        if(Math.abs(firstDerivative.apply(xn1)) < e || Math.abs(xn1 - xn) < e) {
            return new SecantResult(e, xn1, function.apply(xn1), iterations, points);
        }
        xn = xn1;
        iterations++;
        return getXn1(letter);
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
    private boolean canDo() {
        return firstDerivative.apply(a) * firstDerivative.apply(b) < 0;
    }
}
