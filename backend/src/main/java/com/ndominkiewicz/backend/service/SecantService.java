package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.result.SecantResult;
import com.ndominkiewicz.backend.utils.Point;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Service;
import com.ndominkiewicz.backend.model.Motionless;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


@Service
public class SecantService {
    private double a, b, e;
    private double xn, xn1;
    private int iterations;
    private Function<Double, Double> function;
    private Function<Double, Double> firstDerivative;
    private Function<Double, Double> thirdDerivative;
    private final List<Point> points = new ArrayList<>();
    private final List<Point> firstDerPoints = new ArrayList<>();
    private final List<Point> thirdDerPoints = new ArrayList<>();

    public SecantResult solve() {
        System.out.println("=== ROZPOCZĘCIE METODY SIECZNYCH - domyślne parametry ===");
        clearData();
        a = -6;
        b = 1;
        e = 0.00001;
        function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;
        firstDerivative = x -> 3 * Math.pow(x, 2) - 6 * x - 20;
        thirdDerivative = x -> 6.0;

        System.out.println("Parametry początkowe:");
        System.out.println("a = " + a + ", b = " + b + ", ε = " + e);
        System.out.println("f(x) = x³ - 3x² - 20x + 1");
        System.out.println("f'(x) = 3x² - 6x - 20");
        System.out.println("f'''(x) = 6");

        initializeFunctionPoints(500);

        System.out.println("\n=== SPRAWDZENIE WARUNKU POCZĄTKOWEGO ===");
        boolean canProceed = canDo();
        System.out.println("f'(a) = f'(" + a + ") = " + firstDerivative.apply(a));
        System.out.println("f'(b) = f'(" + b + ") = " + firstDerivative.apply(b));
        System.out.println("f'(a) * f'(b) = " + firstDerivative.apply(a) + " * " + firstDerivative.apply(b) +
                " = " + (firstDerivative.apply(a) * firstDerivative.apply(b)) + " < 0? " + canProceed);

        return canProceed ? getXn() : null;
    }

    public SecantResult solve(double a, double b, double e, String firstDerivative, String thirdDerivative) {
        System.out.println("=== ROZPOCZĘCIE METODY SIECZNYCH ===");
        System.out.println("Parametry początkowe:");
        System.out.println("a = " + a + ", b = " + b + ", ε = " + e);
        System.out.println("f'(x) = " + firstDerivative);
        System.out.println("f'''(x) = " + thirdDerivative);

        this.clearData();
        this.a = a;
        this.b = b;
        this.e = e;
        this.firstDerivative = getFunction(firstDerivative);
        this.thirdDerivative = getFunction(thirdDerivative);

        System.out.println("\n=== SPRAWDZENIE WARUNKU POCZĄTKOWEGO ===");
        boolean canProceed = canDo();
        System.out.println("f'(a) = f'(" + a + ") = " + this.firstDerivative.apply(a));
        System.out.println("f'(b) = f'(" + b + ") = " + this.firstDerivative.apply(b));
        System.out.println("f'(a) * f'(b) = " + this.firstDerivative.apply(a) + " * " + this.firstDerivative.apply(b) +
                " = " + (this.firstDerivative.apply(a) * this.firstDerivative.apply(b)) + " < 0? " + canProceed);

        return canProceed ? getXn() : null;
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
            this.firstDerPoints.add(new Point(x, y));
        }

        for(int i = 0; i <= points; i++) {
            double x = a + (range * i / points);
            double y = thirdDerivative.apply(x);
            this.thirdDerPoints.add(new Point(x, y));
        }

        System.out.println("Zainicjalizowano punkty dla funkcji i pochodnych");
    }

    private void clearData() {
        a = 0;
        b = 0;
        e = 0;
        xn = 0;
        xn1 = 0;
        iterations = 0;
        points.clear();
    }

    private SecantResult getXn() {
        System.out.println("\n=== WYBÓR PUNKTU STARTOWEGO ===");
        System.out.println("Sprawdzanie warunku: f'''(a) * f'(a) > 0");
        System.out.println("f'''(a) = f'''(" + a + ") = " + thirdDerivative.apply(a));
        System.out.println("f'(a) = f'(" + a + ") = " + firstDerivative.apply(a));
        System.out.println("f'''(a) * f'(a) = " + thirdDerivative.apply(a) + " * " + firstDerivative.apply(a) +
                " = " + (thirdDerivative.apply(a) * firstDerivative.apply(a)));

        Motionless letter;
        if (thirdDerivative.apply(a) * firstDerivative.apply(a) > 0) {
            xn = b;
            letter = Motionless.A;
            System.out.println("Warunek spełniony → wybrano x₀ = b = " + xn + " (punkt A nieruchomy)");
        } else {
            xn = a;
            letter = Motionless.B;
            System.out.println("Warunek NIE spełniony → wybrano x₀ = a = " + xn + " (punkt B nieruchomy)");
        }

        System.out.println("Punkt startowy: x₀ = " + xn);
        return getXn1(letter);
    }

    private SecantResult getXn1(Motionless letter) {
        System.out.println("\n=== ITERACJA " + (iterations + 1) + " ===");
        System.out.println("Aktualne xₙ = " + xn);
        System.out.println("Nieruchomy punkt: " + (letter == Motionless.A ? "A = " + a : "B = " + b));

        double f_xn = firstDerivative.apply(xn);
        double f_fixed = (letter == Motionless.A) ? firstDerivative.apply(a) : firstDerivative.apply(b);
        double fixed_point = (letter == Motionless.A) ? a : b;

        System.out.println("f'(xₙ) = f'(" + xn + ") = " + f_xn);
        System.out.println("f'(nieruchomy) = f'(" + fixed_point + ") = " + f_fixed);

        switch (letter) {
            case A -> {
                xn1 = xn - (f_xn / (f_xn - f_fixed)) * (xn - a);
                System.out.println("Wzór: xₙ₊₁ = xₙ - [f'(xₙ)/(f'(xₙ) - f'(A))] × (xₙ - A)");
                System.out.println("xₙ₊₁ = " + xn + " - [" + f_xn + "/(" + f_xn + " - " + f_fixed + ")] × (" + xn + " - " + a + ")");
                System.out.println("xₙ₊₁ = " + xn + " - [" + f_xn + "/" + (f_xn - f_fixed) + "] × " + (xn - a));
            }
            case B -> {
                xn1 = xn - (f_xn / (f_xn - f_fixed)) * (b - xn);
                System.out.println("Wzór: xₙ₊₁ = xₙ - [f'(xₙ)/(f'(xₙ) - f'(B))] × (B - xₙ)");
                System.out.println("xₙ₊₁ = " + xn + " - [" + f_xn + "/(" + f_xn + " - " + f_fixed + ")] × (" + b + " - " + xn + ")");
                System.out.println("xₙ₊₁ = " + xn + " - [" + f_xn + "/" + (f_xn - f_fixed) + "] × " + (b - xn));
            }
        }

        System.out.println("xₙ₊₁ = " + xn1);

        double f_xn1 = firstDerivative.apply(xn1);
        double difference = Math.abs(xn1 - xn);

        System.out.println("\n=== SPRAWDZENIE WARUNKÓW STOPU ===");
        System.out.println("f'(xₙ₊₁) = f'(" + xn1 + ") = " + f_xn1);
        System.out.println("Warunek 1: |f'(xₙ₊₁)| < ε");
        System.out.println("|f'(xₙ₊₁)| = |" + f_xn1 + "| = " + Math.abs(f_xn1) + " < " + e + "? " + (Math.abs(f_xn1) < e));

        System.out.println("Warunek 2: |xₙ₊₁ - xₙ| < ε");
        System.out.println("|xₙ₊₁ - xₙ| = |" + xn1 + " - " + xn + "| = " + difference + " < " + e + "? " + (difference < e));

        if(Math.abs(f_xn1) < e || difference < e) {
            System.out.println("\n=== ZAKOŃCZENIE ALGORYTMU ===");
            System.out.println("Warunek stopu spełniony!");
            System.out.println("Ostateczny wynik: x = " + xn1);
            System.out.println("Wartość funkcji: f(x) = " + function.apply(xn1));
            System.out.println("Wartość pochodnej: f'(x) = " + firstDerivative.apply(xn1));
            System.out.println("Liczba iteracji: " + iterations);
            return new SecantResult(e, xn1, function.apply(xn1), iterations, points, firstDerPoints, thirdDerPoints);
        }

        System.out.println("Warunki stopu NIE spełnione - kontynuacja algorytmu");
        xn = xn1;
        iterations++;
        System.out.println("Nowe xₙ = " + xn + ", zwiększono licznik iteracji do: " + iterations);
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