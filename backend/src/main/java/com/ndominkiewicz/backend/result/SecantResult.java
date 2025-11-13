package com.ndominkiewicz.backend.result;

import com.ndominkiewicz.backend.model.Result;
import com.ndominkiewicz.backend.utils.Point;
import java.util.List;

public record SecantResult(double e, double result, double fx, double iterations, List<Point> points, List<Point> firstDerPoints, List<Point> thirdDerPoints) implements Result {
}
