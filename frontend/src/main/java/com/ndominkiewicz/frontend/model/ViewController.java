package com.ndominkiewicz.frontend.model;

import com.ndominkiewicz.frontend.controller.MainController;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public interface ViewController extends Initializable {
    Node getView();
    ViewController getController();
    void setMainController(MainController mainController);
    MainController getMainController();
}
