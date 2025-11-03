package com.ndominkiewicz.backend.model;

import java.util.List;

public record BipartiteResult(String mode, int iterations, double a, double b, double L, double x1, double x2, double xsr) {}