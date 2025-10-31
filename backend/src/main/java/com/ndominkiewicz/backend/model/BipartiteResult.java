package com.ndominkiewicz.backend.model;

import java.util.List;

public record BipartiteResult(double a, double b, double L, double xsr, double x1, double x2, double e, int iterations, List<Point> functionPoints) {}
