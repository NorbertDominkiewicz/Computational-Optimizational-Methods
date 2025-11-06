package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.result.NewtonResult;
import com.ndominkiewicz.backend.utils.Point;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class NewtonService {
    private double a, b, e;
    private double xn, xn1;
    private int iterations;
    private Function<Double, Double> function;
    private Function<Double, Double> firstDerivative;
    private Function<Double, Double> secondDerivative;
    private Function<Double, Double> thirdDerivative;
    private List<Point> points = new ArrayList<>();
    private List<Point> firstDerivativePoints = new ArrayList<>();
    private List<Point> secondDerivativePoints = new ArrayList<>();
    private List<Point> thirdDerivativePoints = new ArrayList<>();
    public NewtonResult solve(double a, double b, double e, String firstDerivative, String secondDerivative, String thirdDerivative) {
        this.clearData();
        this.a = a;
        this.b = b;
        this.e = e;
        this.firstDerivative = getFunction(firstDerivative);
        this.secondDerivative = getFunction(secondDerivative);
        this.thirdDerivative = getFunction(thirdDerivative);
        return canDo() ? getXn() : null;
    }
    public NewtonResult solve() {
        clearData();
        a = -6;
        b = -1;
        e = 0.001;
        function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;
        firstDerivative = x -> 3 * Math.pow(x, 2) - 6 * x - 20;
        secondDerivative = x -> 6 * x - 6;
        thirdDerivative = x -> 6.0;
        initializeFunctionPoints(500);
        return canDo() ? getXn() : null;
    }
    private void clearData() {
        a = 0;
        b = 0;
        e = 0;
        xn = 0;
        xn1 = 0;
        iterations = 0;
        points.clear();
        firstDerivativePoints.clear();
        secondDerivativePoints.clear();
        thirdDerivativePoints.clear();
    }
    public void initializeFunctionPoints(int points) {
        double range = b - a;
        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = function.apply(x);
            this.points.add(new Point(x, y));
        }
        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = firstDerivative.apply(x);
            this.firstDerivativePoints.add(new Point(x, y));
        }
        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = secondDerivative.apply(x);
            this.secondDerivativePoints.add(new Point(x, y));
        }
        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = thirdDerivative.apply(x);
            this.thirdDerivativePoints.add(new Point(x, y));
        }
    }
    private NewtonResult getXn() {
        if (thirdDerivative.apply(a) * firstDerivative.apply(a) > 0) {
            xn = a;
        } else {
            xn = b;
        }
        return getXn1();
    }
    private NewtonResult getXn1() {
        xn1 = xn - (firstDerivative.apply(xn) / secondDerivative.apply(xn));
        if(Math.abs(firstDerivative.apply(xn1)) < e || Math.abs(xn1 - xn) < e) {
            return new NewtonResult(e, xn1, function.apply(xn1), iterations, points, firstDerivativePoints, secondDerivativePoints, thirdDerivativePoints);
        }
        xn = xn1;
        iterations++;
        return getXn1();
    }
    private boolean canDo() {
        return (secondDerivative.apply(a) * secondDerivative.apply(b) >= 0) && (thirdDerivative.apply(a) * thirdDerivative.apply(b) >= 0) && (firstDerivative.apply(a) * firstDerivative.apply(b) < 0);
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
