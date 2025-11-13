package com.ndominkiewicz.backend.result;

import com.ndominkiewicz.backend.model.Result;
import com.ndominkiewicz.backend.utils.Point;
import java.util.List;

public record BipartiteResult(String mode, int iterations, double e, double result, double fx, List<Point> points) implements Result {}