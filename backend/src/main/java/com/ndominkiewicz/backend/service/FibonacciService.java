package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.result.FibonacciResult;
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
    private final List<Point> points = new ArrayList<>();

    public FibonacciResult solve(double a, double b, double e, String equation) {
        System.out.println("=== ROZPOCZĘCIE ALGORYTMU FIBONACCIEGO ===");
        System.out.println("Parametry początkowe:");
        System.out.println("a = " + a + ", b = " + b + ", ε = " + e);
        System.out.println("Równanie: " + equation);

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
        this.initializeFunctionPoints(100);

        System.out.println("\n=== KROK 1: Wyznaczenie n ===");
        n = getN(1, 1, 1);
        System.out.println("Wyznaczone n = " + n);
        System.out.println("F(" + n + ") = " + getFn(n));

        System.out.println("\n=== KROK 2: Obliczenie punktów x1 i x2 ===");
        x1 = getX1();
        x2 = getX2();
        System.out.println("x1 = " + x1 + ", f(x1) = " + function.apply(x1));
        System.out.println("x2 = " + x2 + ", f(x2) = " + function.apply(x2));

        return step3();
    }

    public FibonacciResult solve() {
        System.out.println("=== ROZPOCZĘCIE ALGORYTMU FIBONACCIEGO (domyślne parametry) ===");
        clearData();
        a = -6;
        b = -1;
        e = 0.001;
        function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;
        mode = MINMAX.MAXIMUM;

        System.out.println("Parametry początkowe:");
        System.out.println("a = " + a + ", b = " + b + ", ε = " + e);
        System.out.println("Funkcja: f(x) = x³ - 3x² - 20x + 1");

        initializeFunctionPoints(500);

        System.out.println("\n=== KROK 1: Wyznaczenie n ===");
        n = getN(1, 1, 1);
        System.out.println("Wyznaczone n = " + n);
        System.out.println("F(" + n + ") = " + getFn(n));

        System.out.println("\n=== KROK 2: Obliczenie punktów x1 i x2 ===");
        x1 = getX1();
        x2 = getX2();
        System.out.println("x1 = " + x1 + ", f(x1) = " + function.apply(x1));
        System.out.println("x2 = " + x2 + ", f(x2) = " + function.apply(x2));

        return step3();
    }

    private void clearData() {
        a = 0;
        b = 0;
        e = 0;
        n = 0;
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

    private int getFn(int n) {
        System.out.println("  Obliczanie F(" + n + ")");
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
        System.out.println("  F(" + k + ") = " + F_k1 + " + " + F_k2 + " = " + F_k);
        if(k == n) return F_k;
        else return getFn(k + 1, n, F_k, F_k1);
    }

    private int getN(int n, int F_k1, int F_k2) {
        int F_k = F_k1 + F_k2;
        double condition = (b - a) / F_k;
        System.out.println("  Sprawdzanie n=" + n + ": F(" + n + ")=" + F_k +
                ", warunek: (b-a)/F_n = " + (b-a) + "/" + F_k + " = " + condition +
                " < 2ε = " + (2*e) + " ? " + (condition < 2*e));

        if (condition < 2 * e) return n;
        else return getN(n + 1, F_k, F_k1);
    }

    private double getX1() {
        double F_n_minus_1 = getFn(this.n - 1);
        double F_n = getFn(this.n);
        double result = b - (F_n_minus_1 / F_n) * (b - a);
        System.out.println("  Obliczanie x1: b - (F(" + (n-1) + ")/F(" + n + "))*(b-a) = " +
                b + " - (" + F_n_minus_1 + "/" + F_n + ")*" + (b-a) + " = " + result);
        return result;
    }

    private double getX2() {
        double F_n_minus_1 = getFn(this.n - 1);
        double F_n = getFn(this.n);
        double result = a + (F_n_minus_1 / F_n) * (b - a);
        System.out.println("  Obliczanie x2: a + (F(" + (n-1) + ")/F(" + n + "))*(b-a) = " +
                a + " + (" + F_n_minus_1 + "/" + F_n + ")*" + (b-a) + " = " + result);
        return result;
    }

    private FibonacciResult step3() {
        System.out.println("\n=== ITERACJA " + (iterations + 1) + " ===");
        System.out.println("Przedział: [" + a + ", " + b + "]");
        System.out.println("x1 = " + x1 + ", f(x1) = " + function.apply(x1));
        System.out.println("x2 = " + x2 + ", f(x2) = " + function.apply(x2));
        System.out.println("n = " + n);

        switch (mode) {
            case MAXIMUM -> {
                System.out.println("Tryb: MAXIMUM");
                if(function.apply(x1) > function.apply(x2)) {
                    System.out.println("WARUNEK: f(x1) > f(x2) → b = x2");
                    System.out.println("Stare: a=" + a + ", b=" + b);
                    b = x2;
                    x2 = x1;
                    System.out.println("Nowe: a=" + a + ", b=" + b + ", x2=" + x2);
                    x1 = getX1();
                } else {
                    System.out.println("WARUNEK: f(x1) ≤ f(x2) → a = x1");
                    System.out.println("Stare: a=" + a + ", b=" + b);
                    a = x1;
                    x1 = x2;
                    System.out.println("Nowe: a=" + a + ", b=" + b + ", x1=" + x1);
                    x2 = getX2();
                }
                n--;
                System.out.println("Zmniejszono n do: " + n);
            }
            case MINIMUM -> {
                System.out.println("Tryb: MINIMUM");
                if(function.apply(x1) < function.apply(x2)) {
                    System.out.println("WARUNEK: f(x1) < f(x2) → b = x2");
                    System.out.println("Stare: a=" + a + ", b=" + b);
                    b = x2;
                    x2 = x1;
                    System.out.println("Nowe: a=" + a + ", b=" + b + ", x2=" + x2);
                    x1 = getX1();
                } else {
                    System.out.println("WARUNEK: f(x1) ≥ f(x2) → a = x1");
                    System.out.println("Stare: a=" + a + ", b=" + b);
                    a = x1;
                    x1 = x2;
                    System.out.println("Nowe: a=" + a + ", b=" + b + ", x1=" + x1);
                    x2 = getX2();
                }
                n--;
                System.out.println("Zmniejszono n do: " + n);
            }
        }
        return step4();
    }

    private FibonacciResult step4() {
        System.out.println("\n=== SPRAWDZENIE WARUNKU STOPU ===");
        System.out.println("|x2 - x1| = |" + x2 + " - " + x1 + "| = " + Math.abs(x2 - x1) + " < ε = " + e + " ? " + (Math.abs(x2 - x1) < e));
        System.out.println("n = " + n + " == 1 ? " + (n == 1));

        if ((Math.abs(x2 - x1) < e) || n == 1) {
            double result = (a + b) / 2;
            System.out.println("\n=== ZAKOŃCZENIE ALGORYTMU ===");
            System.out.println("Ostateczny przedział: [" + a + ", " + b + "]");
            System.out.println("Wynik: x = " + result);
            System.out.println("Wartość funkcji: f(x) = " + function.apply(result));
            System.out.println("Liczba iteracji: " + iterations);
            return new FibonacciResult("Maksimum", e, result, function.apply(result), iterations, points);
        }
        iterations++;
        System.out.println("Kontynuacja algorytmu...");
        return step3();
    }
}