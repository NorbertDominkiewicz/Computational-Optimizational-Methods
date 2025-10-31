package com.ndominkiewicz.frontend.controller.view;

import com.ndominkiewicz.frontend.Launcher;
import com.ndominkiewicz.frontend.model.ViewController;
import com.ndominkiewicz.frontend.model.Page;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    /*FXML*/
    @FXML
    Button closeButton;
    @FXML
    StackPane mainPane;
    @FXML
    Label title;
    @FXML
    private Button bipartite;
    @FXML
    private Button bisection;
    @FXML
    private Button fibonacci;
    //
    BipartiteController bipartiteController;
    BisectionController bisectionController;
    List<ViewController> controllers;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllers = new ArrayList<>();
        loadUpActions();
        closeButton.setOnAction(event -> Launcher.close());
        switchView(Page.BIPARTITE);
    }
    private void loadUpActions() {
        bipartite.setOnAction(event -> switchView(Page.BIPARTITE));
        fibonacci.setOnAction( event -> switchView(Page.FIBONACCI));
        bisection.setOnAction( event -> switchView(Page.BISECTION));
    }
    private void changeTitle(String value) {
        Platform.runLater(() -> title.setText(value));
    }
    public void switchView(Page view) {
        String name = "";
        mainPane.getChildren().clear();
        switch (view) {
            case BIPARTITE -> {
                if (bipartiteController == null) {
                    name = "bipartite";
                    changeTitle("Metoda Dwudzielna");
                    ViewController controller = loadController(name);
                    bipartiteController = (BipartiteController) controller;
                    controllers.add(bipartiteController);
                } else {
                    mainPane.getChildren().add(bipartiteController.getView());
                    changeTitle("Metoda Dwudzielna");
                }
            }
            case BISECTION -> {
                if (bisectionController == null) {
                    name = "bisection";
                    changeTitle("Metoda Bisekcji");
                    ViewController controller = loadController(name);
                    bisectionController = (BisectionController) controller;
                    controllers.add(bisectionController);
                } else {
                    mainPane.getChildren().add(bisectionController.getView());
                    changeTitle("Metoda Bisekcji");
                }
            }
        }
    }
    private ViewController loadController(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ndominkiewicz/frontend/fxml/views/" + name + ".fxml"));
            Node node = loader.load();
            ViewController controller = loader.getController();
            mainPane.getChildren().add(node);
            return controller;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
