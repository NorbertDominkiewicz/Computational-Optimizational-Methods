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
        System.out.println("=== ROZPOCZĘCIE METODY ZŁOTEGO PODZIAŁU ===");
        System.out.println("Parametry początkowe:");
        System.out.println("a = " + a + ", b = " + b + ", ε = " + e);
        System.out.println("Równanie: " + equation);

        this.clearData();
        this.a = a;
        this.b = b;
        this.e = e;
        this.k = getK();
        this.mode = MINMAX.MAXIMUM;
        this.function = getFunction(equation);

        System.out.println("Współczynnik złotego podziału k = (√5 - 1)/2 = " + k);
        System.out.println("Tryb: " + mode);

        return step1();
    }

    public GoldenRatioResult solve() {
        System.out.println("=== ROZPOCZĘCIE METODY ZŁOTEGO PODZIAŁU - domyślne parametry ===");
        clearData();
        a = -6;
        b = -1;
        e = 0.001;
        k = getK();
        mode = MINMAX.MAXIMUM;
        function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;

        System.out.println("Parametry początkowe:");
        System.out.println("a = " + a + ", b = " + b + ", ε = " + e);
        System.out.println("f(x) = x³ - 3x² - 20x + 1");
        System.out.println("Współczynnik złotego podziału k = (√5 - 1)/2 = " + k);
        System.out.println("Tryb: " + mode);

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
        System.out.println("\n=== INICJALIZACJA PUNKTÓW WYKRESU ===");
        double range = b - a;
        System.out.println("Generowanie " + points + " punktów w przedziale [" + a + ", " + b + "]");

        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = function.apply(x);
            this.points.add(new Point(x, y));
        }
        System.out.println("Zainicjalizowano " + this.points.size() + " punktów funkcji");
    }

    private GoldenRatioResult step1() {
        System.out.println("\n=== KROK 1: Obliczenie punktów x1 i x2 ===");
        x1 = getX1();
        x2 = getX2();

        System.out.println("x1 = b - k(b - a) = " + b + " - " + k + "×(" + b + " - " + a + ") = " + x1);
        System.out.println("x2 = a + k(b - a) = " + a + " + " + k + "×(" + b + " - " + a + ") = " + x2);
        System.out.println("f(x1) = " + function.apply(x1) + ", f(x2) = " + function.apply(x2));

        return step2();
    }

    private double getX1() {
        return b - k * (b - a);
    }

    private double getX2() {
        return a + k * (b - a);
    }

    private GoldenRatioResult step2() {
        System.out.println("\n=== ITERACJA " + (iterations + 1) + " ===");
        System.out.println("Przedział: [" + a + ", " + b + "]");
        System.out.println("x1 = " + x1 + ", f(x1) = " + function.apply(x1));
        System.out.println("x2 = " + x2 + ", f(x2) = " + function.apply(x2));

        switch (mode) {
            case MAXIMUM -> {
                System.out.println("Tryb: MAXIMUM");
                if(function.apply(x1) > function.apply(x2)) {
                    System.out.println("WARUNEK: f(x1) > f(x2) → b = x2");
                    System.out.println("Stare: a = " + a + ", b = " + b);
                    b = x2;
                    x2 = x1;
                    System.out.println("Nowe: a = " + a + ", b = " + b + ", x2 = " + x2);
                    x1 = getX1();
                    System.out.println("Nowe x1 = " + x1);
                } else {
                    System.out.println("WARUNEK: f(x1) ≤ f(x2) → a = x1");
                    System.out.println("Stare: a = " + a + ", b = " + b);
                    a = x1;
                    x1 = x2;
                    System.out.println("Nowe: a = " + a + ", b = " + b + ", x1 = " + x1);
                    x2 = getX2();
                    System.out.println("Nowe x2 = " + x2);
                }
            }
            case MINIMUM -> {
                System.out.println("Tryb: MINIMUM");
                if(function.apply(x1) < function.apply(x2)) {
                    System.out.println("WARUNEK: f(x1) < f(x2) → b = x2");
                    System.out.println("Stare: a = " + a + ", b = " + b);
                    b = x2;
                    x2 = x1;
                    System.out.println("Nowe: a = " + a + ", b = " + b + ", x2 = " + x2);
                    x1 = getX1();
                    System.out.println("Nowe x1 = " + x1);
                } else {
                    System.out.println("WARUNEK: f(x1) ≥ f(x2) → a = x1");
                    System.out.println("Stare: a = " + a + ", b = " + b);
                    a = x1;
                    x1 = x2;
                    System.out.println("Nowe: a = " + a + ", b = " + b + ", x1 = " + x1);
                    x2 = getX2();
                    System.out.println("Nowe x2 = " + x2);
                }
            }
        }

        double difference = Math.abs(x2 - x1);
        System.out.println("\n=== SPRAWDZENIE WARUNKU STOPU ===");
        System.out.println("|x2 - x1| = |" + x2 + " - " + x1 + "| = " + difference);
        System.out.println("Warunek: |x2 - x1| < ε ? " + difference + " < " + e + " ? " + (difference < e));

        if(difference < e) {
            double result = (a + b) / 2;
            System.out.println("\n=== ZAKOŃCZENIE ALGORYTMU ===");
            System.out.println("Warunek stopu spełniony!");
            System.out.println("Ostateczny przedział: [" + a + ", " + b + "]");
            System.out.println("Wynik: x = (a + b)/2 = (" + a + " + " + b + ")/2 = " + result);
            System.out.println("Wartość funkcji: f(x) = " + function.apply(result));
            System.out.println("Liczba iteracji: " + iterations);
            return new GoldenRatioResult("Maksimum", e, result, function.apply(result), iterations, points);
        }

        System.out.println("Warunek stopu NIE spełniony - kontynuacja algorytmu");
        iterations++;
        System.out.println("Zwiększono licznik iteracji do: " + iterations);
        return step2();
    }

    private double getK() {
        return (Math.sqrt(5) - 1) / 2;
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