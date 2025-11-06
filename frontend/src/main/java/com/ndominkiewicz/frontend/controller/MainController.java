package com.ndominkiewicz.frontend.controller;

import com.ndominkiewicz.frontend.Launcher;
import com.ndominkiewicz.frontend.controller.view.*;
import com.ndominkiewicz.frontend.model.View;
import com.ndominkiewicz.frontend.model.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.*;

/**
 * @author Norbert Dominkiewicz
 */

public class MainController implements Initializable {
    private final Map<View, ViewController> views = new TreeMap<>();
    private View currentView;
    /**
     * FXML Elements
     * */
    @FXML private FlowPane asidePanel;
    @FXML private Label viewLabel;
    @FXML private StackPane contentContainer;
    /**
     * Following injections will refer to Button JavaFX objects.
     * Some of it will direct us towards different view and the others
     * are for app based controls like closeButton
     */
    @FXML private Button homeButton;
    @FXML private Button bipartiteButton;
    @FXML private Button bisectionButton;
    @FXML private Button newtonButton;
    @FXML private Button secantButton;
    @FXML private Button goldenratioButton;
    @FXML private Button fibonacciButton;
    @FXML private Button closeButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initActions();
        initViews();
        changeView(View.HOME);
    }
    /**
     * Method that loads up listeners for actions for various elements at once
     */
    private void initActions() {
        closeButton.setOnAction(actionEvent -> Launcher.close());
        homeButton.setOnAction(actionEvent -> changeView(View.HOME));
        //
        bipartiteButton.setOnAction(actionEvent -> changeView(View.BIPARTITE));
        bisectionButton.setOnAction( actionEvent -> changeView(View.BISECTION));
        newtonButton.setOnAction(actionEvent -> changeView(View.NEWTON));
        secantButton.setOnAction(actionEvent -> changeView(View.SECANT));
        goldenratioButton.setOnAction(actionEvent -> changeView(View.GOLDENRATIO));
    }
    /**
     * Method that loads up all views possible to use at once
     */
    private void initViews() {
        initView(View.HOME);
        initView(View.BIPARTITE);
        initView(View.BISECTION);
        initView(View.NEWTON);
        initView(View.SECANT);
        initView(View.GOLDENRATIO);
    }

    /**
     * Method that loads up the view given as an argument.
     * The crucial here is method getController() which returns us controller
     * based on view and its controller from fxml file
     */
    private void initView(View view) {
        ViewController controller = getController(view);
        switch (view) {
            case HOME -> {
                HomeController homeController = (HomeController) controller;
                homeController.setMainController(this);
                views.put(view, homeController);
            }
            case BIPARTITE -> {
                BipartiteController bipartiteController = (BipartiteController) controller;
                bipartiteController.setMainController(this);
                views.put(view, bipartiteController);
            }
            case BISECTION -> {
                BisectionController bisectionController = (BisectionController) controller;
                bisectionController.setMainController(this);
                views.put(view, bisectionController);
            }
            case NEWTON -> {
                NewtonController newtonController = (NewtonController) controller;
                newtonController.setMainController(this);
                views.put(view, newtonController);
            }
            case SECANT -> {
                SecantController secantController = (SecantController) controller;
                secantController.setMainController(this);
                views.put(view, secantController);
            }
            case GOLDENRATIO -> {
                GoldenRatioController goldenRatioController = (GoldenRatioController) controller;
                goldenRatioController.setMainController(this);
                views.put(view, goldenRatioController);
            }
        }
    }
    private ViewController getController(View view) {
        try {
            String fxml = "";
            switch (view) {
                case HOME -> fxml = "home";
                case BISECTION -> fxml = "bisection";
                case BIPARTITE -> fxml = "bipartite";
                case NEWTON -> fxml = "newton";
                case SECANT -> fxml = "secant";
                case GOLDENRATIO -> fxml = "goldenratio";
            }
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("/com/ndominkiewicz/frontend/fxml/views/" + fxml + ".fxml"));
            Node node = loader.load();
            return loader.getController();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    /**
     * Method that allows user to change view.
     * First it checks if the given view is actually being displayed, if not
     * it seeks for a view in views Map that seeks with a given view and sets up
     * a new Node
     */
    private void changeView(View view) {
        if(!(view.equals(currentView))) {
            contentContainer.getChildren().clear();
            for(Map.Entry<View, ViewController> entry : views.entrySet()) {
                if(entry.getKey().equals(view)) {
                    Node node = entry.getValue().getView();
                    contentContainer.getChildren().add(node);
                    contentContainer.setAlignment(Pos.CENTER);
                    currentView = view;
                    changeTitle(currentView);
                    changeActiveStyle(currentView);
                }
            }
        }
    }
    /**
     * Method for changing the label of a current view with a given value
     */
    private void changeTitle(View view) {
        switch (view) {
            case HOME -> Platform.runLater(() -> viewLabel.setText("Home"));
            case BIPARTITE -> Platform.runLater(() -> viewLabel.setText("Metoda Dwudzielna"));
            case BISECTION -> Platform.runLater(() -> viewLabel.setText("Metoda Bisekcji"));
            case NEWTON -> Platform.runLater(() -> viewLabel.setText("Metoda Stycznych"));
            case SECANT -> Platform.runLater(() -> viewLabel.setText("Metoda Siecznych"));
            case GOLDENRATIO -> Platform.runLater(() -> viewLabel.setText("Metoda Złotego Podziału"));
        }
    }
    private void changeActiveStyle(View view) {
        homeButton.getStyleClass().remove("active");
        bipartiteButton.getStyleClass().remove("active");
        bisectionButton.getStyleClass().remove("active");
        newtonButton.getStyleClass().remove("active");
        secantButton.getStyleClass().remove("active");
        goldenratioButton.getStyleClass().remove("active");
        switch (view) {
            case HOME -> homeButton.getStyleClass().add("active");
            case BIPARTITE -> bipartiteButton.getStyleClass().add("active");
            case BISECTION -> bisectionButton.getStyleClass().add("active");
            case NEWTON -> newtonButton.getStyleClass().add("active");
            case SECANT -> secantButton.getStyleClass().add("active");
            case GOLDENRATIO -> goldenratioButton.getStyleClass().add("active");
        }
    }
    /**
     * Method for adding results calculated in each of optimization method
     * It provies the type from the controller and just result
     *
     */
    public void addRecentResult(String method, double result) {
        HomeController homeController = (HomeController) views.get(View.HOME);
        homeController.addRecentResult(method, result);
    }
}
