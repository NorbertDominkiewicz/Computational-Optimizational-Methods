package com.ndominkiewicz.frontend.model;

import com.ndominkiewicz.frontend.utils.Point;

import java.util.List;

public record BipartiteResult(double a, double b, double L, double xsr, double x1, double x2, double e, int iterations, List<Point> functionPoints) {
    public int getIterations() {
        return iterations;
    }
}
