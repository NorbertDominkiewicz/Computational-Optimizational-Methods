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
        System.out.println("=== ROZPOCZĘCIE METODY BISEKCJI ===");
        System.out.println("Parametry początkowe:");
        System.out.println("a = " + a + ", b = " + b + ", ε = " + e);
        System.out.println("Równanie pochodnej: " + equation);

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

        System.out.println("\n=== SPRAWDZENIE WARUNKU POCZĄTKOWEGO ===");
        boolean canProceed = canDo();
        System.out.println("f'(a) * f'(b) = " + derFunction.apply(a) + " * " + derFunction.apply(b) +
                " = " + (derFunction.apply(a) * derFunction.apply(b)) + " < 0 ? " + canProceed);

        return canProceed ? step1() : null;
    }

    public BisectionResult solve(){
        System.out.println("=== ROZPOCZĘCIE METODY BISEKCJI (domyślne parametry) ===");
        clearData();
        a = -6;
        b = -1;
        e = 0.1;
        function = x -> Math.pow(x, 3) - 3 * Math.pow(x, 2) - 20 * x + 1;
        derFunction = x -> 3 * Math.pow(x, 2) - 6 * x - 20;

        System.out.println("Parametry początkowe:");
        System.out.println("a = " + a + ", b = " + b + ", ε = " + e);
        System.out.println("Funkcja: f(x) = x³ - 3x² - 20x + 1");
        System.out.println("Pochodna: f'(x) = 3x² - 6x - 20");

        initializeFunctionPoints(500);

        System.out.println("\n=== SPRAWDZENIE WARUNKU POCZĄTKOWEGO ===");
        boolean canProceed = canDo();
        System.out.println("f'(a) = f'(" + a + ") = " + derFunction.apply(a));
        System.out.println("f'(b) = f'(" + b + ") = " + derFunction.apply(b));
        System.out.println("f'(a) * f'(b) = " + derFunction.apply(a) + " * " + derFunction.apply(b) +
                " = " + (derFunction.apply(a) * derFunction.apply(b)) + " < 0 ? " + canProceed);

        return canProceed ? step1() : null;
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
        System.out.println("\n=== ITERACJA " + iterations + " - KROK 1 ===");
        System.out.println("Aktualny przedział: [" + a + ", " + b + "]");

        xsr = (a + b) / 2;
        System.out.println("Obliczanie xśr: (a + b)/2 = (" + a + " + " + b + ")/2 = " + xsr);
        System.out.println("f'(xśr) = f'(" + xsr + ") = " + derFunction.apply(xsr));

        return step2();
    }

    BisectionResult step2() {
        System.out.println("=== ITERACJA " + iterations + " - KROK 2 ===");

        if(derFunction.apply(xsr) == 0) {
            System.out.println("ZNALEZIONO MIEJSCE ZEROWE! f'(xśr) = 0");
            System.out.println("x = " + xsr + ", f(x) = " + function.apply(xsr));
            return new BisectionResult(e, xsr, function.apply(xsr), iterations, functionPoints, derFunctionPoints);
        } else {
            double fa = derFunction.apply(a);
            double fxsr = derFunction.apply(xsr);
            double product = fa * fxsr;

            System.out.println("Sprawdzanie znaku:");
            System.out.println("f'(a) = " + fa + ", f'(xśr) = " + fxsr);
            System.out.println("f'(a) * f'(xśr) = " + fa + " * " + fxsr + " = " + product);

            if (product < 0) {
                System.out.println("WARUNEK: f'(a) * f'(xśr) < 0 → b = xśr");
                System.out.println("Stare: a = " + a + ", b = " + b);
                b = xsr;
                System.out.println("Nowe: a = " + a + ", b = " + b);
            } else {
                System.out.println("WARUNEK: f'(a) * f'(xśr) ≥ 0 → a = xśr");
                System.out.println("Stare: a = " + a + ", b = " + b);
                a = xsr;
                System.out.println("Nowe: a = " + a + ", b = " + b);
            }
        }
        return step3();
    }

    BisectionResult step3() {
        System.out.println("=== ITERACJA " + iterations + " - KROK 3 ===");

        double absDerivative = Math.abs(derFunction.apply(xsr));
        System.out.println("Sprawdzanie warunku stopu:");
        System.out.println("|f'(xśr)| = |" + derFunction.apply(xsr) + "| = " + absDerivative);
        System.out.println("Warunek: |f'(xśr)| < ε ? " + absDerivative + " < " + e + " ? " + (absDerivative < e));

        if(absDerivative < e){
            System.out.println("\n=== ZAKOŃCZENIE ALGORYTMU ===");
            System.out.println("Warunek stopu spełniony!");
            System.out.println("Ostateczny przedział: [" + a + ", " + b + "]");
            System.out.println("Przybliżone miejsce zerowe: x = " + xsr);
            System.out.println("Wartość funkcji: f(x) = " + function.apply(xsr));
            System.out.println("Wartość pochodnej: f'(x) = " + derFunction.apply(xsr));
            System.out.println("Liczba iteracji: " + iterations);
            return new BisectionResult(e, xsr, function.apply(xsr), iterations, functionPoints, derFunctionPoints);
        }

        System.out.println("Warunek stopu NIE spełniony - kontynuacja algorytmu");
        iterations++;
        System.out.println("Zwiększono licznik iteracji do: " + iterations);
        return step1();
    }

    public void initializeFunctionPoints(int points) {
        System.out.println("\n=== INICJALIZACJA PUNKTÓW WYKRESU ===");
        double range = b - a;
        System.out.println("Generowanie " + points + " punktów dla funkcji i pochodnej w przedziale [" + a + ", " + b + "]");

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

        System.out.println("Zainicjalizowano " + functionPoints.size() + " punktów funkcji");
        System.out.println("Zainicjalizowano " + derFunctionPoints.size() + " punktów pochodnej");
    }
}