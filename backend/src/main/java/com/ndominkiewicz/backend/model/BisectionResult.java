package com.ndominkiewicz.backend.model;

import com.ndominkiewicz.backend.utils.Point;

import java.util.List;

public record BisectionResult(double a, double b, double e, double xsr, double fx, int iterations, List<Point> functionPoints) implements Result {

}
