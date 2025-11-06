package com.ndominkiewicz.backend.model;

import com.ndominkiewicz.backend.utils.Point;

import java.util.List;

public record SecantResult(double e, double result, double fx, double iterations, List<Point> points) implements Result{
}
