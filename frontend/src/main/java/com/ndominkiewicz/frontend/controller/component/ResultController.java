package com.ndominkiewicz.frontend.controller.component;

import com.ndominkiewicz.frontend.controller.view.*;
import com.ndominkiewicz.frontend.model.*;
import com.ndominkiewicz.frontend.result.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements ComponentController {
    private ViewController viewController;
    @FXML private VBox resultContainer;
    @FXML private GridPane root;
    @FXML private Button returnButton;
    public Node getView() {
        return root;
    }
    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initActions();
    }
    private void initActions() {
        returnButton.setOnAction(actionEvent -> {
            clearResults();
            if(viewController instanceof BipartiteController bipartiteController) {
                bipartiteController.swapComponent(Component.ENTRY);
                bipartiteController.clearPoints();
            }
            if(viewController instanceof NewtonController newtonController) {
                newtonController.swapComponent(Component.ENTRY);
                newtonController.clearPoints();
            }
            if(viewController instanceof BisectionController bisectionController) {
                bisectionController.swapComponent(Component.ENTRY);
                bisectionController.clearPoints();
            }
            if(viewController instanceof SecantController secantController) {
                secantController.swapComponent(Component.ENTRY);
                secantController.clearPoints();
            }
            if(viewController instanceof GoldenRatioController goldenRatioController) {
                goldenRatioController.swapComponent(Component.ENTRY);
                goldenRatioController.clearPoints();
            }
        });
    }

    public void clearResults() {
        resultContainer.getChildren().clear();
    }

    public void addResults(NewtonResult newtonResult) {
        resultContainer.getChildren().add(createResult("Dokładkość (e):", newtonResult.e()));
        resultContainer.getChildren().add(createResult("Wynik:", newtonResult.result()));
        resultContainer.getChildren().add(createResult("f(x):", newtonResult.fx()));
        resultContainer.getChildren().add(createResult("Iteracji:", newtonResult.iterations()));
    }

    public void addResults(SecantResult secantResult) {
        resultContainer.getChildren().add(createResult("Dokładkość (e):", secantResult.e()));
        resultContainer.getChildren().add(createResult("Wynik:", secantResult.result()));
        resultContainer.getChildren().add(createResult("f(x):", secantResult.fx()));
        resultContainer.getChildren().add(createResult("Iteracji:", secantResult.iterations()));
    }

    public void addResults(BisectionResult bisectionResult) {
        resultContainer.getChildren().add(createResult("Dokładkość (e):", bisectionResult.e()));
        resultContainer.getChildren().add(createResult("Wynik:", bisectionResult.xsr()));
        resultContainer.getChildren().add(createResult("f(x):", bisectionResult.fx()));
        resultContainer.getChildren().add(createResult("Iteracji:", bisectionResult.iterations()));
    }

    public void addResults(GoldenRatioResult goldenRatioResult) {
        resultContainer.getChildren().add(createResult("Dokładkość (e):", goldenRatioResult.e()));
        resultContainer.getChildren().add(createResult("Wynik:", goldenRatioResult.result()));
        resultContainer.getChildren().add(createResult("f(x):", goldenRatioResult.fx()));
        resultContainer.getChildren().add(createResult("Iteracji:", goldenRatioResult.iterations()));
    }

    public void addResults(BipartiteResult bipartiteResult) {
        resultContainer.getChildren().add(createResult("Dokładkość (e):", bipartiteResult.e()));
        resultContainer.getChildren().add(createResult("Wynik:", bipartiteResult.result()));
        resultContainer.getChildren().add(createResult("f(x):", bipartiteResult.fx()));
        resultContainer.getChildren().add(createResult("Iteracji:", bipartiteResult.iterations()));
    }

    private Node createResult(String label, String value) {
        String style = "-fx-text-fill: white";
        Label left = new Label(label);
        left.setAlignment(Pos.CENTER);
        left.setStyle(style);
        Label right = new Label(value);
        right.setAlignment(Pos.CENTER);
        right.setStyle(style);
        HBox resultBox = new HBox(left, right);
        resultBox.setAlignment(Pos.CENTER);
        return resultBox;
    }

    private Node createResult(String label, double value) {
        String style = "result-label";
        Label left = new Label(label);
        left.setAlignment(Pos.CENTER);
        left.getStyleClass().add(style);
        Label right = new Label(String.valueOf(value));
        right.setAlignment(Pos.CENTER);
        right.getStyleClass().add(style);
        HBox resultBox = new HBox(left, right);
        resultBox.setAlignment(Pos.CENTER);
        resultBox.setSpacing(5);
        return resultBox;
    }
}
