package com.ndominkiewicz.backend.model;

import java.util.List;

public record BisectionResult(double xsr, int iterations, List<Point> functionPoints, List<Point> derFunctionPoints) {

}
