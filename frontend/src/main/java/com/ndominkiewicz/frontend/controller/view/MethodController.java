package com.ndominkiewicz.frontend.controller.view;

import com.ndominkiewicz.frontend.Launcher;
import com.ndominkiewicz.frontend.controller.MainController;
import com.ndominkiewicz.frontend.controller.component.EntryController;
import com.ndominkiewicz.frontend.controller.component.ResultController;
import com.ndominkiewicz.frontend.model.*;
import com.ndominkiewicz.frontend.service.BipartiteService;
import com.ndominkiewicz.frontend.utils.Point;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;


/**
 * @author Norbert Dominkiewicz
 */

public class MethodController implements ViewController {
    private final BipartiteService bipartiteService = new BipartiteService();
    private final Map<Component, ComponentController> components = new TreeMap<>();
    private Component currentComponent;
    private View view;
    /**
     * Controllers
     */
    private MainController mainController;
    /**
     * FXML Elements
     */
    @FXML private BorderPane chartContainer;
    @FXML private FlowPane componentsContainer;
    @FXML private GridPane root;
    /**
     * Chart
     */
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private LineChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private List<XYChart.Data<Number, Number>> functionPoints = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComponents();
        initChart();
        swapComponent(Component.ENTRY);
    }
    private void initChart() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        chart = new LineChart<>(xAxis, yAxis);
        chart.setAnimated(true);
        xAxis.setLabel("x");
        yAxis.setLabel("f(x)");
        xAxis.setTickUnit(5);
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        chartContainer.setCenter(chart);
    }
    public void setView(View view) {
        this.view = view;
    }
    public void updateXBounds(double xMin, double xMax) {
        Platform.runLater(() -> {
            xAxis.setLowerBound(xMin * 2);
            xAxis.setUpperBound(xMax < 0 ? Math.abs(xMax) : xMax * 2);
        });
    }
    private void updateYBounds(double yMax) {
        Platform.runLater(() -> {
            yAxis.setUpperBound(yMax < 0 ? Math.abs(yMax) : yMax * 1.1115);
        });
    }
    /**
     * Method that loads up all components for this controller
     */
    private void initComponents() {
        initComponent(Component.ENTRY);
        initComponent(Component.RESULT);
    }
    /**
     * Method that loads up the component given as an argument.
     * The crucial here is method getController() which returns us controller
     * based on view and its controller from fxml file
     */
    private void initComponent(Component component) {
        ComponentController controller = getController(component);
        switch (component) {
            case ENTRY -> {
                EntryController entryController = (EntryController) controller;
                entryController.setViewController(this);
                components.put(component, entryController);
            }
            case RESULT -> {
                ResultController resultController = (ResultController) controller;
                resultController.setViewController(this);
                components.put(component, resultController);
            }
        }
    }
    private ComponentController getController(Component component) {
        try {
            String fxml = "";
            switch (component) {
                case ENTRY -> fxml = "entry";
                case RESULT -> fxml = "result";
            }
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("/com/ndominkiewicz/frontend/fxml/components/" + fxml + ".fxml"));
            Node node = loader.load();
            return loader.getController();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public void swapComponent(Component component) {
        if(!(component.equals(currentComponent))) {
            componentsContainer.getChildren().clear();
            for(Map.Entry<Component, ComponentController> entry : components.entrySet()) {
                if(entry.getKey().equals(component)) {
                    Node node = entry.getValue().getView();
                    componentsContainer.getChildren().add(node);
                    componentsContainer.setAlignment(Pos.CENTER);
                    currentComponent = component;
                }
            }
        }
    }
    @Override
    public Node getView() {
        return root;
    }
    @Override
    public ViewController getController() {
        return this;
    }
    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public MainController getMainController() {
        return mainController;
    }
    public void onCalculate() {
        BipartiteResult result;
        swapComponent(Component.RESULT);
        EntryController entryController = (EntryController) components.get(Component.ENTRY);
        if(entryController.getEquationField().getText().isEmpty()){
            result = bipartiteService.calculate();
        } else {
            result = bipartiteService.calculate(entryController.getData());
        }
        initializeFunctionPoints(result.points(), result.xsr(), result.fx());
        updateXBounds(-6, -1);
        updateYBounds(result.fx());
        ResultController resultController = (ResultController) components.get(Component.RESULT);
        resultController.updateLabels(result.mode(), result.iterations(), result.a(), result.b(), result.L(), result.x1(), result.x2(), result.xsr());
        mainController.addRecentResult("Metoda Dwudzielna: ", result.fx());
    }
    private void initializeFunctionPoints(List<Point> points, double optimumX, double optimumY) {
        functionPoints.clear();
        series.getData().clear();
        for (Point point : points) {
            functionPoints.add(new XYChart.Data<>(point.getX(), point.getY()));
        }
        series.getData().addAll(functionPoints);
        XYChart.Data<Number, Number> optimumPoint = new XYChart.Data<>(optimumX, optimumY);
        optimumPoint.setNode(createCustomNode("red"));
        series.getData().add(optimumPoint);
        series.setName("Result f(x) = " + optimumY);
        if (!chart.getData().contains(series)) {
            chart.getData().add(series);
        }
    }
    private Node createCustomNode(String color) {
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(5);
        circle.setFill(javafx.scene.paint.Color.valueOf(color));
        circle.setStroke(javafx.scene.paint.Color.BLACK);
        return circle;
    }
}
