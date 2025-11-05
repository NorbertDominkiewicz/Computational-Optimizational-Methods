package com.ndominkiewicz.frontend.controller.view;

import com.ndominkiewicz.frontend.controller.MainController;
import com.ndominkiewicz.frontend.model.ViewController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements ViewController {
    private MainController mainController;
    @FXML private GridPane root;
    @FXML private VBox recentResultsContainer;
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
    public void addRecentResult(String method, double result) {
        String style = "-fx-text-fill: white";
        Label id = new Label(String.valueOf(recentResultsContainer.getChildren().size() + 1) + ".");
        id.setAlignment(Pos.CENTER);
        id.setStyle(style);
        Label methodLabel = new Label(method);
        methodLabel.setAlignment(Pos.CENTER);
        methodLabel.setStyle(style);
        Label resultLabel = new Label(String.valueOf(result));
        resultLabel.setAlignment(Pos.CENTER);
        resultLabel.setStyle(style);
        HBox resultContainer = new HBox(id, methodLabel, resultLabel);
        resultContainer.setAlignment(Pos.CENTER);
        resultContainer.setSpacing(4);
        recentResultsContainer.getChildren().add(resultContainer);
    }
}
