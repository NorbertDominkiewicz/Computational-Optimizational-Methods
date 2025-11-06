package com.ndominkiewicz.backend.result;

import com.ndominkiewicz.backend.model.Result;
import com.ndominkiewicz.backend.utils.Point;

import java.util.List;

public record BisectionResult(double e, double xsr, double fx, int iterations, List<Point> functionPoints, List<Point> derFunctionPoints) implements Result { }
