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
        System.out.println("=== ROZPOCZĘCIE METODY STYCZNYCH (NEWTONA) ===");
        System.out.println("Parametry początkowe:");
        System.out.println("a = " + a + ", b = " + b + ", ε = " + e);
        System.out.println("f'(x) = " + firstDerivative);
        System.out.println("f''(x) = " + secondDerivative);
        System.out.println("f'''(x) = " + thirdDerivative);

        this.clearData();
        this.a = a;
        this.b = b;
        this.e = e;
        this.firstDerivative = getFunction(firstDerivative);
        this.secondDerivative = getFunction(secondDerivative);
        this.thirdDerivative = getFunction(thirdDerivative);

        System.out.println("\n=== SPRAWDZENIE WARUNKÓW POCZĄTKOWYCH ===");
        boolean canProceed = canDo();
        System.out.println("Warunki metody Newtona:");
        System.out.println("1. f''(a) * f''(b) ≥ 0? " + this.secondDerivative.apply(a) + " * " + this.secondDerivative.apply(b) +
                " = " + (this.secondDerivative.apply(a) * this.secondDerivative.apply(b)) + " ≥ 0? " +
                (this.secondDerivative.apply(a) * this.secondDerivative.apply(b) >= 0));
        System.out.println("2. f'''(a) * f'''(b) ≥ 0? " + this.thirdDerivative.apply(a) + " * " + this.thirdDerivative.apply(b) +
                " = " + (this.thirdDerivative.apply(a) * this.thirdDerivative.apply(b)) + " ≥ 0? " +
                (this.thirdDerivative.apply(a) * this.thirdDerivative.apply(b) >= 0));
        System.out.println("3. f'(a) * f'(b) < 0? " + this.firstDerivative.apply(a) + " * " + this.firstDerivative.apply(b) +
                " = " + (this.firstDerivative.apply(a) * this.firstDerivative.apply(b)) + " < 0? " +
                (this.firstDerivative.apply(a) * this.firstDerivative.apply(b) < 0));
        System.out.println("Wszystkie warunki spełnione? " + canProceed);

        return canProceed ? getXn() : null;
    }

    public NewtonResult solve() {
        System.out.println("=== ROZPOCZĘCIE METODY STYCZNYCH (NEWTONA) - domyślne parametry ===");
        clearData();
        a = -6;
        b = -1;
        e = 0.00001;
        function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;
        firstDerivative = x -> 3 * Math.pow(x, 2) - 6 * x - 20;
        secondDerivative = x -> 6 * x - 6;
        thirdDerivative = x -> 6.0;

        System.out.println("Parametry początkowe:");
        System.out.println("a = " + a + ", b = " + b + ", ε = " + e);
        System.out.println("f(x) = x³ - 3x² - 20x + 1");
        System.out.println("f'(x) = 3x² - 6x - 20");
        System.out.println("f''(x) = 6x - 6");
        System.out.println("f'''(x) = 6");

        initializeFunctionPoints(500);

        System.out.println("\n=== SPRAZDZENIE WARUNKÓW POCZĄTKOWYCH ===");
        boolean canProceed = canDo();
        System.out.println("Warunki metody Newtona:");
        System.out.println("1. f''(a) * f''(b) ≥ 0? " + secondDerivative.apply(a) + " * " + secondDerivative.apply(b) +
                " = " + (secondDerivative.apply(a) * secondDerivative.apply(b)) + " ≥ 0? " +
                (secondDerivative.apply(a) * secondDerivative.apply(b) >= 0));
        System.out.println("2. f'''(a) * f'''(b) ≥ 0? " + thirdDerivative.apply(a) + " * " + thirdDerivative.apply(b) +
                " = " + (thirdDerivative.apply(a) * thirdDerivative.apply(b)) + " ≥ 0? " +
                (thirdDerivative.apply(a) * thirdDerivative.apply(b) >= 0));
        System.out.println("3. f'(a) * f'(b) < 0? " + firstDerivative.apply(a) + " * " + firstDerivative.apply(b) +
                " = " + (firstDerivative.apply(a) * firstDerivative.apply(b)) + " < 0? " +
                (firstDerivative.apply(a) * firstDerivative.apply(b) < 0));
        System.out.println("Wszystkie warunki spełnione? " + canProceed);

        return canProceed ? getXn() : null;
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
        System.out.println("\n=== INICJALIZACJA PUNKTÓW WYKRESU ===");
        double range = b - a;
        System.out.println("Generowanie " + points + " punktów w przedziale [" + a + ", " + b + "]");

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

        System.out.println("Zainicjalizowano punkty dla funkcji i pochodnych");
    }

    private NewtonResult getXn() {
        System.out.println("\n=== WYBÓR PUNKTU STARTOWEGO ===");
        System.out.println("Sprawdzanie warunku: f'''(a) * f'(a) > 0");
        System.out.println("f'''(a) = f'''(" + a + ") = " + thirdDerivative.apply(a));
        System.out.println("f'(a) = f'(" + a + ") = " + firstDerivative.apply(a));
        System.out.println("f'''(a) * f'(a) = " + thirdDerivative.apply(a) + " * " + firstDerivative.apply(a) +
                " = " + (thirdDerivative.apply(a) * firstDerivative.apply(a)));

        if (thirdDerivative.apply(a) * firstDerivative.apply(a) > 0) {
            xn = a;
            System.out.println("Warunek spełniony → wybrano x₀ = a = " + xn);
        } else {
            xn = b;
            System.out.println("Warunek NIE spełniony → wybrano x₀ = b = " + xn);
        }

        System.out.println("Punkt startowy: x₀ = " + xn);
        return getXn1();
    }

    private NewtonResult getXn1() {
        System.out.println("\n=== ITERACJA " + (iterations + 1) + " ===");
        System.out.println("Aktualne xₙ = " + xn);

        double f_prim = firstDerivative.apply(xn);
        double f_bis = secondDerivative.apply(xn);

        System.out.println("f'(xₙ) = f'(" + xn + ") = " + f_prim);
        System.out.println("f''(xₙ) = f''(" + xn + ") = " + f_bis);

        xn1 = xn - (f_prim / f_bis);

        System.out.println("Obliczanie xₙ₊₁ = xₙ - f'(xₙ)/f''(xₙ)");
        System.out.println("xₙ₊₁ = " + xn + " - (" + f_prim + ")/(" + f_bis + ") = " + xn1);

        double f_prim_xn1 = firstDerivative.apply(xn1);
        double difference = Math.abs(xn1 - xn);

        System.out.println("\n=== SPRAWDZENIE WARUNKÓW STOPU ===");
        System.out.println("Warunek 1: |f'(xₙ₊₁)| < ε");
        System.out.println("|f'(xₙ₊₁)| = |" + f_prim_xn1 + "| = " + Math.abs(f_prim_xn1) + " < " + e + "? " + (Math.abs(f_prim_xn1) < e));

        System.out.println("Warunek 2: |xₙ₊₁ - xₙ| < ε");
        System.out.println("|xₙ₊₁ - xₙ| = |" + xn1 + " - " + xn + "| = " + difference + " < " + e + "? " + (difference < e));

        if(Math.abs(f_prim_xn1) < e || difference < e) {
            System.out.println("\n=== ZAKOŃCZENIE ALGORYTMU ===");
            System.out.println("Warunek stopu spełniony!");
            System.out.println("Ostateczny wynik: x = " + xn1);
            System.out.println("Wartość funkcji: f(x) = " + function.apply(xn1));
            System.out.println("Wartość pochodnej: f'(x) = " + firstDerivative.apply(xn1));
            System.out.println("Liczba iteracji: " + iterations);
            return new NewtonResult(e, xn1, function.apply(xn1), iterations, points, firstDerivativePoints, secondDerivativePoints, thirdDerivativePoints);
        }

        System.out.println("Warunki stopu NIE spełnione - kontynuacja algorytmu");
        xn = xn1;
        iterations++;
        System.out.println("Nowe xₙ = " + xn + ", zwiększono licznik iteracji do: " + iterations);
        return getXn1();
    }

    private boolean canDo() {
        return (secondDerivative.apply(a) * secondDerivative.apply(b) >= 0) &&
                (thirdDerivative.apply(a) * thirdDerivative.apply(b) >= 0) &&
                (firstDerivative.apply(a) * firstDerivative.apply(b) < 0);
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
