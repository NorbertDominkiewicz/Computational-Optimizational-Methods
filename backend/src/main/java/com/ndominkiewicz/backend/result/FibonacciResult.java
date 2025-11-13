package com.ndominkiewicz.backend.result;

import com.ndominkiewicz.backend.model.Result;
import com.ndominkiewicz.backend.utils.Point;

import java.util.List;

public record FibonacciResult(String mode, double e, double result, double fx, int iterations, List<Point> points) implements Result {}
