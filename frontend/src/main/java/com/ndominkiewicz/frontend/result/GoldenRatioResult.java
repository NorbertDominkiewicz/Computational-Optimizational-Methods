package com.ndominkiewicz.frontend.result;

import com.ndominkiewicz.frontend.utils.Point;

import java.util.List;

public record GoldenRatioResult(String mode, double e, double result, double fx, int iterations, List<Point> points) {
}
