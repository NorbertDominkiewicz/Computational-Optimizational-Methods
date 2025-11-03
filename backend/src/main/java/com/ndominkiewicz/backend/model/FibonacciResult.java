package com.ndominkiewicz.backend.model;

import com.ndominkiewicz.backend.utils.Point;

import java.util.List;

public record FibonacciResult(String mode, int iterations, double a, double b, double e, double x1, double x2, double result, List<Point> points) implements Result {}
