package com.ndominkiewicz.frontend.model;


import com.ndominkiewicz.frontend.utils.Point;

import java.util.List;

public record BipartiteResult(String mode, int iterations, double a, double b, double L, double x1, double x2, double xsr, double fx, List<Point> points){}
