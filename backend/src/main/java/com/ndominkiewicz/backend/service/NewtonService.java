package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.model.NewtonResult;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class NewtonService {
    private double a, b, e;
    private double xn, xn1;
    private int iterations;
    private Function<Double, Double> firstDerivative;
    private Function<Double, Double> secondDerivative;
    private Function<Double, Double> thirdDerivative;
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
    private void clearData() {
        a = 0;
        b = 0;
        e = 0;
        xn = 0;
        xn1 = 0;
        iterations = 0;
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
            return new NewtonResult(a, b, e, xn1, iterations);
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
