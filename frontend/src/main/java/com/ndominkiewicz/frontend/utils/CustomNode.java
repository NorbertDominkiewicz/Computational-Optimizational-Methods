package com.ndominkiewicz.frontend.utils;

import javafx.scene.Node;

public class CustomNode {
    public static Node createCircle(String color) {
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(5);
        circle.setFill(javafx.scene.paint.Color.valueOf(color));
        circle.setStroke(javafx.scene.paint.Color.BLACK);
        return circle;
    }
}
