package com.ndominkiewicz.frontend.model;

import javafx.scene.Node;

public interface ViewController {
    Node getView();
    void onCalculate();
}
