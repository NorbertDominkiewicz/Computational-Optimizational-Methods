package com.ndominkiewicz.backend.model;

public record SecantResult(double a, double b, double e, double xsr, double iterations) implements Result{
}
