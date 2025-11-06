package com.ndominkiewicz.backend.model;

import com.ndominkiewicz.backend.utils.Point;

import java.util.List;

/**
 * @param a
 * @param b
 * @param e
 * @param xsr
 * @param iterations
 */
public record NewtonResult(double e, double result, double fx, double iterations, List<Point> points) implements Result{}
