package com.ndominkiewicz.frontend.result;


import com.ndominkiewicz.frontend.utils.Point;

import java.util.List;

public record BipartiteResult(String mode, int iterations, double e, double result, double fx, List<Point> points) {}
