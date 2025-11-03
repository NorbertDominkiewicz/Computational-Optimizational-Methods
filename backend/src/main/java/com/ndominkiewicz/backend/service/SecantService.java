package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.model.NewtonResult;
import com.ndominkiewicz.backend.model.SecantResult;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

enum Motionless {
    A, B
}

@Service
public class SecantService {
    private double a, b, e;
    private double xn, xn1;
    private int iterations;
    private Function<Double, Double> firstDerivative;
    private Function<Double, Double> thirdDerivative;
    public SecantResult solve(double a, double b, double e, String firstDerivative, String thirdDerivative) {
        this.clearData();
        this.a = a;
        this.b = b;
        this.e = e;
        this.firstDerivative = getFunction(firstDerivative);
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
            return new SecantResult(a, b, e, xn1, iterations);
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
