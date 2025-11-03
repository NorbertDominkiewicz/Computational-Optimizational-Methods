package com.ndominkiewicz.backend.model;

/**
 * @param a
 * @param b
 * @param e
 * @param xsr
 * @param iterations
 */
public record NewtonResult(double a, double b, double e, double xsr, double iterations) implements Result{}
