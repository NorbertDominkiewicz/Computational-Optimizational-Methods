package com.ndominkiewicz.frontend.utils;

public class Point {
    private final double x, y;
    private boolean inCircle;

    public Point(double x, double y, boolean inCircle) {
        this.x = x;
        this.y = y;
        this.inCircle = inCircle;
    }
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isInCircle() { return inCircle; }
}
