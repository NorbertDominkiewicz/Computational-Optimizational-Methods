package com.ndominkiewicz.frontend.result;

import com.ndominkiewicz.frontend.utils.Point;
import java.util.List;

public record NewtonResult(double e, double result, double fx, double iterations, List<Point> points, List<Point> firstDerivativePoints, List<Point> secondDerivativePoints, List<Point> thirdDerivativePoints) {}
