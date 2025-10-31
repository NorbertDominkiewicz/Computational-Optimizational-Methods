package com.ndominkiewicz.backend.model;

public class Point {
    private final double x, y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getY() {
        return y;
    }
    public double getX() {
        return x;
    }
}
