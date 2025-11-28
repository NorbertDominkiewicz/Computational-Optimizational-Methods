package com.ndominkiewicz.backend.service.SeveralVariable;

import java.util.function.BiFunction;

public class NewtonService {
    BiFunction<Double, Double, Double> function = (x, y) -> 10 * x * x + 12 * x * y + 10 * y * y;;
    BiFunction<Double, Double, Double> firstDerivativeX = (x, y) -> 20  * x + 12 * y;
    BiFunction<Double, Double, Double> firstDerivativeY = (x, y) -> 12 * x + 20 * y;
    BiFunction<Double, Double, Double> secondDerivativeX = (x, y) -> 20.0;
    BiFunction<Double, Double, Double> secondDerivativeY = (x, y) -> 20.0;
    BiFunction<Double, Double, Double> derivativeXY = (x, y) -> 12.0;
    double [] x0 = new double[]{10, 12};
    double [] x1 = new double[2];
    private double[] createGradient() {
        return new double[]{firstDerivativeX.apply(x0[0], x0[1]), firstDerivativeY.apply(x0[0], x0[1])};
    }

    private double[][] hessMatrix() {
        return new double[][] {
                { secondDerivativeX.apply(x0[0], x0[1]), derivativeXY.apply(x0[0], x0[1]) },
                { derivativeXY.apply(x0[0], x0[1]), secondDerivativeY.apply(x0[0], x0[1]) }
        };
    }

    private double[][] inverseHessian() {
        double a = secondDerivativeX.apply(x0[0], x0[1]);
        double b = derivativeXY.apply(x0[0], x0[1]);
        double c = derivativeXY.apply(x0[0], x0[1]);
        double d = secondDerivativeY.apply(x0[0], x0[1]);

        double det = a * d - b * c;

        double coefficient = 1.0 / det;

        return new double[][] {
                { coefficient * d, -coefficient * b },
                { -coefficient * c, coefficient * a }
        };
    }

    public void run(double e) {
        while (true) {
            double[] grad = createGradient();
            double[][] invH = inverseHessian();
            if (Math.sqrt(grad[0]*grad[0] + grad[1]*grad[1]) <= e) {
                break;
            }
            x1[0] = x0[0] - (invH[0][0] * grad[0] + invH[0][1] * grad[1]);
            x1[1] = x0[1] - (invH[1][0] * grad[0] + invH[1][1] * grad[1]);
            if (Math.abs(x1[0] - x0[0]) <= e && Math.abs(x1[1] - x0[1]) <= e) {
                break;
            }
            x0[0] = x1[0];
            x0[1] = x1[1];
            System.out.println("x = (" + x0[0] + ", " + x0[1] + ")");
        }
    }
    public static void main(String[] args) {
        NewtonService newtonService = new NewtonService();
        newtonService.run(3);
    }
}

