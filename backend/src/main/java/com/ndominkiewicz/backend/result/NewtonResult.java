package com.ndominkiewicz.backend.result;

import com.ndominkiewicz.backend.model.Result;
import com.ndominkiewicz.backend.utils.Point;
import java.util.List;

public record NewtonResult(double e, double result, double fx, double iterations, List<Point> points, List<Point> firstDerivativePoints, List<Point> secondDerivativePoints, List<Point> thirdDerivativePoints) implements Result {}
