package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.result.BisectionResult;
import com.ndominkiewicz.backend.utils.Point;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class BisectionService {
    private double a, b, e;
    private double xsr;
    private int iterations;
    private Function<Double, Double> function;
    private Function<Double, Double> derFunction;
    private final List<Point> functionPoints = new ArrayList<>();
    private final List<Point> derFunctionPoints = new ArrayList<>();
    public BisectionResult solve(double a, double b, double e, String equation){
        this.a = a;
        this.b = b;
        this.e = e;
        this.iterations = 1;
        this.function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;
        this.derFunction = x -> {
            Expression expression = new ExpressionBuilder(equation)
                    .variable("x").build().setVariable("x", x);
            return expression.evaluate();
        };
        this.initializeFunctionPoints(100);
        return canDo() ? step1() : null;
    }
    public BisectionResult solve(){
        clearData();
        a = -6;
        b = -1;
        e = 0.001;
        function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;
        derFunction = x -> 3 * Math.pow(x, 2) - 6 * x - 20;
        initializeFunctionPoints(500);
        return canDo() ? step1() : null;
    }
    private void clearData() {
        a = 0;
        b = 0;
        e = 0;
        xsr = 0;
        iterations = 0;
        functionPoints.clear();
    }
    private boolean canDo() {
        return derFunction.apply(a) * derFunction.apply(b) < 0;
    }
    BisectionResult step1() {
        xsr = (a + b) / 2;
        return  step2();
    }
    BisectionResult step2() {
        if(derFunction.apply(xsr) == 0) {
            return new BisectionResult(e, xsr, function.apply(xsr), iterations, functionPoints, derFunctionPoints);
        } else {
            if (derFunction.apply(xsr) * derFunction.apply(a) < 0) {
                b = xsr;
            } else {
                a = xsr;
            }
        }
        return step3();
    }
    BisectionResult step3() {
        if(Math.abs(derFunction.apply(xsr)) < e){
            return new BisectionResult(e, xsr, function.apply(xsr), iterations, functionPoints, derFunctionPoints);
        }
        iterations ++;
        return step1();
    }
    public void initializeFunctionPoints(int points) {
        double range = b - a;
        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = function.apply(x);
            functionPoints.add(new Point(x, y));
        }
        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = derFunction.apply(x);
            derFunctionPoints.add(new Point(x, y));
        }
    }
}
