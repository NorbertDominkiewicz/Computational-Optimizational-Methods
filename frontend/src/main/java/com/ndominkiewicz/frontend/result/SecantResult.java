package com.ndominkiewicz.frontend.result;

import com.ndominkiewicz.frontend.utils.Point;

import java.util.List;

public record SecantResult(double e, double result, double fx, double iterations, List<Point> points, List<Point> firstDerPoints, List<Point> thirdDerPoints) { }
