package com.ndominkiewicz.frontend.result;

import com.ndominkiewicz.frontend.utils.Point;

import java.util.List;

public record BisectionResult(double a, double b, double e, double xsr, double fx, int iterations, List<Point> functionPoints) {
}
