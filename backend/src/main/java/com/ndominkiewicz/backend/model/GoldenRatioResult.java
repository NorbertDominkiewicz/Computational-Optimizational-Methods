package com.ndominkiewicz.backend.model;

public record GoldenRatioResult(String mode, double a, double b, double e, double result, int iterations) implements Result{
}
