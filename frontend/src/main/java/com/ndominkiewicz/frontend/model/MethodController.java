package com.ndominkiewicz.frontend.model;

import com.ndominkiewicz.frontend.utils.Point;

import java.util.List;

public interface MethodController {
    void onCalculate();
    void loadPoints(List<Point> points, double optimumX, double optimumY);
    void initComponents();
    void initComponent(Component component);
    void swapComponent(Component component);
    void clearPoints();
    void setUpChart();
    void updateXBounds(Double xMin, Double xMax);
    void updateYBounds(Double yMin, Double yMax);
    ComponentController getController(Component component);
}
