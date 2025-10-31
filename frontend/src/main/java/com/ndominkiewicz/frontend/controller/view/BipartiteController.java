package com.ndominkiewicz.frontend.controller.view;

import com.ndominkiewicz.frontend.controller.component.EntryController;
import com.ndominkiewicz.frontend.model.BipartiteResult;
import com.ndominkiewicz.frontend.model.Roll;
import com.ndominkiewicz.frontend.model.ViewController;
import com.ndominkiewicz.frontend.service.BipartiteService;
import com.ndominkiewicz.frontend.utils.CLI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class BipartiteController implements ViewController {
    private Roll roll;
    private EntryController entryController;
    private final BipartiteService service = new BipartiteService();
    private final ArrayList<Node> nodes = new ArrayList<>();
    @FXML
    private StackPane rollContainer;
    @FXML
    private GridPane root;
    @FXML
    private LineChart<Number, Number> chart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    public void addComponents(Roll roll) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ndominkiewicz/frontend/fxml/components/" + (roll.equals(Roll.ENTRY) ? "entry" : "result") + ".fxml"));
            Node element = loader.load();
            nodes.add(element);
            entryController = loader.getController();
            entryController.setController(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void switchComponents(Roll roll) {
        rollContainer.getChildren().clear();
        switch (roll) {
            case ENTRY -> rollContainer.getChildren().add(nodes.getFirst());
            case RESULT -> {}
        }
    }
    public void initialize() {
        addComponents(Roll.ENTRY);
        switchComponents(Roll.ENTRY);
        chart.setAnimated(true);
        xAxis.setLabel("x");
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(1);
        yAxis.setLabel("f(x)");
    }
    @Override
    public Node getView() {
        return root;
    }
    public void onCalculate() {
//        String result = service.calculate(
//                Double.parseDouble(entryController.getA()),
//                Double.parseDouble(entryController.getB()),
//                Double.parseDouble(entryController.getEpsilon())
//        );
//        CLI.log(result);
        CLI.log(service.calculate());
    }
}
