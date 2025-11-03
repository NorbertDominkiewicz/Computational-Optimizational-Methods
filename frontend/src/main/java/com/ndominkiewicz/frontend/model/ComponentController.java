package com.ndominkiewicz.frontend.model;

import javafx.fxml.Initializable;
import javafx.scene.Node;

public interface ComponentController extends Initializable {
    Node getView();
}
