package com.ndominkiewicz.frontend.controller.view;

import com.ndominkiewicz.frontend.Launcher;
import com.ndominkiewicz.frontend.controller.MainController;
import com.ndominkiewicz.frontend.controller.component.EntryController;
import com.ndominkiewicz.frontend.controller.component.ResultController;
import com.ndominkiewicz.frontend.exception.ServerNotConnected;
import com.ndominkiewicz.frontend.model.*;
import com.ndominkiewicz.frontend.result.NewtonResult;
import com.ndominkiewicz.frontend.service.NewtonService;
import com.ndominkiewicz.frontend.utils.CustomNode;
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

public class NewtonController implements ViewController, MethodController {
    @FXML private GridPane root;
    @FXML private FlowPane componentsContainer;
    @FXML private BorderPane chartContainer;
    private final NewtonService newtonService = new NewtonService();
    private final Map<Component, ComponentController> components = new TreeMap<>();
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
    private final XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> firstDerSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> secondDerSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> thirdDerSeries = new XYChart.Series<>();
    private final List<XYChart.Data<Number, Number>> functionPoints = new ArrayList<>();
    private MainController mainController;
    private Component currentComponent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initComponents();
            setUpChart();
            swapComponent(Component.ENTRY);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initComponents() {
        initComponent(Component.ENTRY);
        initComponent(Component.RESULT);
    }

    @Override
    public void initComponent(Component component) {
        ComponentController controller = getController(component);
        switch (component) {
            case ENTRY -> {
                EntryController entryController = (EntryController) controller;
                entryController.setMethodController(this);
                components.put(component, entryController);
            }
            case RESULT -> {
                ResultController resultController = (ResultController) controller;
                resultController.setViewController(this);
                components.put(component, resultController);
            }
        }
    }

    @Override
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
    public ComponentController getController(Component component) {
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

    @Override
    public void setUpChart() {
        chart.setAnimated(true);
        xAxis.setLabel("x");
        xAxis.setTickUnit(5);
        xAxis.setAutoRanging(false);
        yAxis.setLabel("f(x)");
        yAxis.setTickUnit(5);
        yAxis.setAutoRanging(false);
        if(chartContainer.getChildren().isEmpty()) {
            chartContainer.setCenter(chart);
        }
    }
    public void loadPoints(List<Point> points, List<Point> firstDerPoints, List<Point> secondDerPoints, List<Point> thirdDerPoints,  double optimumX, double optimumY) {
        clearPoints();
        for(Point point : points) functionPoints.add(new XYChart.Data<>(point.getX(), point.getY()));
        for(Point point : firstDerPoints) firstDerSeries.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        for(Point point : secondDerPoints) secondDerSeries.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        for(Point point : thirdDerPoints) thirdDerSeries.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        XYChart.Data<Number, Number> optimumPoint = new XYChart.Data<>(optimumX, optimumY);
        optimumPoint.setNode(CustomNode.createCircle("green"));
        series.getData().addAll(functionPoints);
        series.getData().add(optimumPoint);
        series.setName("f(x) = " + optimumY);
        firstDerSeries.setName("f'(x)");
        secondDerSeries.setName("f''(x)");
        thirdDerSeries.setName("f'''(x)");
        if (!(chart.getData().contains(series))) {
            chart.getData().addAll(series, firstDerSeries, secondDerSeries, thirdDerSeries);
        }
    }

    @Override
    public void clearPoints() {
        functionPoints.clear();
        series.getData().clear();
        firstDerSeries.getData().clear();
        secondDerSeries.getData().clear();
        thirdDerSeries.getData().clear();
    }
    @Override
    public void updateXBounds(Double xMin, Double xMax) {
        Platform.runLater(() -> {
            if (xMin != null) xAxis.setLowerBound(xMin);
            if (xMax != null) xAxis.setUpperBound(xMax < 0 ? Math.abs(xMax) : xMax);
        });
    }

    @Override
    public void updateYBounds(Double yMin, Double yMax) {
        Platform.runLater(() -> {
            if (yMin != null) yAxis.setLowerBound(yMin);
            if (yMax != null) yAxis.setUpperBound(yMax < 0 ? Math.abs(yMax) * 5 : yMax * 5);
        });
    }
    @Override
    public void onCalculate() {
        NewtonResult newtonResult = null;
        EntryController entryController;
        try {
            entryController = (EntryController) components.get(Component.ENTRY);
            String [] data = entryController.getData();
            String a = data[2];
            String b = data[3];
            String e = data[1];
            String equation = data[0];
            if (e.isEmpty() && a.isEmpty() && b.isEmpty() && equation.isEmpty()) {
                // sample already with arguments
                newtonResult = newtonService.calculate();
                updateXBounds((double) -6, (double) -1);
                updateYBounds((double) - 220, (double) 30);
                loadPoints(newtonResult.points(), newtonResult.firstDerivativePoints(), newtonResult.secondDerivativePoints(), newtonResult.thirdDerivativePoints(), newtonResult.result(),newtonResult.fx());
            }
            else {
                if (e.isEmpty()) {
                    newtonResult = newtonService.calculate(Double.parseDouble(a), Double.parseDouble(b), equation);
                }
                if (e.isEmpty() && a.isEmpty() && b.isEmpty()) {
                    newtonResult = newtonService.calculate(equation);
                }
                if (!(e.isEmpty() && a.isEmpty() && b.isEmpty() && equation.isEmpty())) {
                    newtonResult = newtonService.calculate(Double.parseDouble(a), Double.parseDouble(b), Double.parseDouble(e), equation);
                }
                updateXBounds(Double.parseDouble(a), Double.parseDouble(b));
                updateYBounds(null, newtonResult.fx());
            }
            swapComponent(Component.RESULT);
            ResultController resultController = (ResultController) components.get(Component.RESULT);
            resultController.addResults(newtonResult);
            mainController.addRecentResult("Metoda stycznych: ", newtonResult.fx());
            if (newtonResult == null) {
                throw new ServerNotConnected("Could not connect the API");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadPoints(List<Point> points, double optimumX, double optimumY) {

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

    @Override
    public MainController getMainController() {
        return mainController;
    }

}
