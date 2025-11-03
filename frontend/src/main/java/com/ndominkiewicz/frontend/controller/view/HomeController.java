package com.ndominkiewicz.frontend.controller.view;

import com.ndominkiewicz.frontend.controller.MainController;
import com.ndominkiewicz.frontend.model.ViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements ViewController {
    private MainController mainController;
    @FXML private GridPane root;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @Override
    public Node getView() {
        return root;
    }
    @Override
    public ViewController getController() {
        return null;
    }
    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public MainController getMainController() {
        return mainController;
    }
}
