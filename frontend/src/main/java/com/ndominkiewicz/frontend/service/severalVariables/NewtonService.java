package com.ndominkiewicz.frontend.service.severalVariables;

import java.util.function.BiFunction;

enum Mode {
    Analytic,
    Numeric
}

enum DerivativeOrder {
    I, II
}

class PartialDerivative {
    private static final double h = 0.00001;

    public static double calculateX(BiFunction<Double, Double, Double> f, double x, double y, DerivativeOrder o) {
        return switch (o) {
            case I -> (f.apply(x + h, y) - f.apply(x, y)) / h;
            case II -> (f.apply(x + 2*h, y) - 2*f.apply(x + h, y) + f.apply(x, y)) / (h*h);
        };
    }

    public static double calculateY(BiFunction<Double, Double, Double> f, double x, double y, DerivativeOrder o) {
        return switch (o) {
            case I -> (f.apply(x, y + h) - f.apply(x, y)) / h;
            case II -> (f.apply(x, y + 2*h) - 2*f.apply(x, y + h) + f.apply(x, y)) / (h*h);
        };
    }

    public static double calculateXY(BiFunction<Double, Double, Double> f, double x, double y) {
        return (f.apply(x + h, y + h) - f.apply(x + h, y) - f.apply(x, y + h) + f.apply(x, y)) / (h*h);
    }
}

public class NewtonService {

    BiFunction<Double, Double, Double> function =
            (x, y) -> y*y*y + x*x - 9*x*y - 3*x + 2;

    BiFunction<Double, Double, Double> dfx =
            (x, y) -> 2*x - 9*y - 3;
    BiFunction<Double, Double, Double> dfy =
            (x, y) -> 3*y*y - 9*x;

    BiFunction<Double, Double, Double> d2fxx =
            (x, y) -> 2.0;
    BiFunction<Double, Double, Double> d2fyy =
            (x, y) -> 6*y;
    BiFunction<Double, Double, Double> d2fxy =
            (x, y) -> -9.0;

    double[] x0 = new double[]{3, 7};
    double[] x1 = new double[2];


    private double[] gradient(Mode mode) {
        if (mode == Mode.Analytic) {
            return new double[]{
                    dfx.apply(x0[0], x0[1]),
                    dfy.apply(x0[0], x0[1])
            };
        } else {
            return new double[]{
                    PartialDerivative.calculateX(function, x0[0], x0[1], DerivativeOrder.I),
                    PartialDerivative.calculateY(function, x0[0], x0[1], DerivativeOrder.I)
            };
        }
    }

    private double[][] hessian(Mode mode) {
        if (mode == Mode.Analytic) {
            return new double[][]{
                    { d2fxx.apply(x0[0], x0[1]), d2fxy.apply(x0[0], x0[1]) },
                    { d2fxy.apply(x0[0], x0[1]), d2fyy.apply(x0[0], x0[1]) }
            };
        } else {
            return new double[][]{
                    { PartialDerivative.calculateX(function, x0[0], x0[1], DerivativeOrder.II),
                            PartialDerivative.calculateXY(function, x0[0], x0[1]) },
                    { PartialDerivative.calculateXY(function, x0[0], x0[1]),
                            PartialDerivative.calculateY(function, x0[0], x0[1], DerivativeOrder.II) }
            };
        }
    }

    private double[][] inverse(double[][] H) {
        double a = H[0][0];
        double b = H[0][1];
        double c = H[1][0];
        double d = H[1][1];

        double det = a * d - b * c;
        if (det == 0) throw new RuntimeException("Hessian is singular!");

        double k = 1.0 / det;

        return new double[][]{
                { k * d, -k * b },
                { -k * c, k * a }
        };
    }

    public void run(Mode mode, double e) {

        while (true) {
            double[] grad = gradient(mode);
            double normGrad = Math.sqrt(grad[0]*grad[0] + grad[1]*grad[1]);

            if (normGrad <= e) break;

            double[][] H = hessian(mode);
            double[][] invH = inverse(H);

            // compute x1 = x0 - H⁻¹ * grad
            x1[0] = x0[0] - (invH[0][0]*grad[0] + invH[0][1]*grad[1]);
            x1[1] = x0[1] - (invH[1][0]*grad[0] + invH[1][1]*grad[1]);

            if (Math.abs(x1[0] - x0[0]) <= e && Math.abs(x1[1] - x0[1]) <= e) break;

            x0[0] = x1[0];
            x0[1] = x1[1];

            System.out.println("x = (" + x0[0] + ", " + x0[1] + ")");
        }

        System.out.println("\nMinimum approx at: (" + x0[0] + ", " + x0[1] + ")");
    }

    public static void main(String[] args) {
        NewtonService newton = new NewtonService();

        System.out.println("=== ANALYTIC ===");
        newton.run(Mode.Analytic, 0.001);

        System.out.println("\n=== NUMERIC ===");
        newton.run(Mode.Numeric, 0.001);
    }
}
