package com.ndominkiewicz.backend.service;

import com.ndominkiewicz.backend.model.DerivativeOrder;
import com.ndominkiewicz.backend.utils.Matrix;
import com.ndominkiewicz.backend.utils.PartialDerivative;

import java.util.function.BiFunction;

public class SeveralVariablesService {
    BiFunction<Double, Double, Double> function;
    double [] x0;
    double epsilon;
    public void solve(BiFunction<Double, Double, Double> function, double [] x0, double epsilon) {
        this.function = function;
        this.x0 = x0;
        this.epsilon = epsilon;
        this.run();
    }
    public void run() {
        double [] xk = x0.clone();
        double [] xk1;
        int maxIterations = 100;
        for (int i = 0; i < maxIterations; i++) {
            double[] gradient = calculateGradient(xk);

            if (norm(gradient) <= epsilon) {
                System.out.println("Zbieżność osiągnięta po " + i + " iteracjach");
                System.out.println("Wynik: " + xk[0] + ", " + xk[1]);
                return;
            }
            double[][] hessian = calculateHessian(xk);
            xk1 = calculateNextPoint(xk, hessian, gradient);
            if (distance(xk1, xk) <= epsilon) {
                System.out.println("Zbieżność osiągnięta po " + i + " iteracjach");
                System.out.println("Wynik: " + xk[0] + ", " + xk[1]);
                return;
            }
            xk = xk1;
        }
        System.out.println("Osiągnięto maksymalną liczbę iteracji");
        System.out.println("Wynik: " + xk[0] + ", " + xk[1]);
    }
    private double[] calculateGradient(double[] point) {
        double x = point[0];
        double y = point[1];
        double df_dx = PartialDerivative.calculateX(function, x, y, DerivativeOrder.I);
        double df_dy = PartialDerivative.calculateY(function, x, y, DerivativeOrder.I);
        return new double[]{df_dx, df_dy};
    }
    private double[][] calculateHessian(double[] point) {
        double x = point[0];
        double y = point[1];
        double d2f_dx2 = PartialDerivative.calculateX(function, x, y, DerivativeOrder.II);
        double d2f_dy2 = PartialDerivative.calculateY(function, x, y, DerivativeOrder.II);
        double d2f_dxdy = PartialDerivative.calculateXY(function, x, y);
        return new double[][]{
                {d2f_dx2, d2f_dxdy},
                {d2f_dxdy, d2f_dy2}
        };
    }
    private double[] calculateNextPoint(double[] xk, double[][] hessian, double[] gradient) {
        Matrix hessianMatrix = new Matrix(new double[2][2]);
        hessianMatrix.setWartosc(0, 0, hessian[0][0]);
        hessianMatrix.setWartosc(0, 1, hessian[0][1]);
        hessianMatrix.setWartosc(1, 0, hessian[1][0]);
        hessianMatrix.setWartosc(1, 1, hessian[1][1]);

        hessianMatrix.reverse();

        double[] hessianInverseTimesGradient = multiplyMatrixVector(hessianMatrix.tab, gradient);

        double[] xk1 = new double[2];
        xk1[0] = xk[0] - hessianInverseTimesGradient[0];
        xk1[1] = xk[1] - hessianInverseTimesGradient[1];

        return xk1;
    }
    private double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
        double[] result = new double[2];
        result[0] = matrix[0][0] * vector[0] + matrix[0][1] * vector[1];
        result[1] = matrix[1][0] * vector[0] + matrix[1][1] * vector[1];
        return result;
    }
    private double norm(double[] vector) {
        return Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1]);
    }

    private double distance(double[] a, double[] b) {
        return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
    }
    public static void main(String [] args) {
        BiFunction<Double, Double, Double> function = (x, y) -> 10 * x * x + 12 * x * y + 10 * y * y;
        double[] x0 = {10.0, 12.0};
        SeveralVariablesService service = new SeveralVariablesService();
        service.solve(function, x0, 0.01);
    }
}
