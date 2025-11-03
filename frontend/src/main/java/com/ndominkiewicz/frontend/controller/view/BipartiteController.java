package com.ndominkiewicz.frontend.controller.view;

import com.ndominkiewicz.frontend.Launcher;
import com.ndominkiewicz.frontend.controller.MainController;
import com.ndominkiewicz.frontend.controller.component.EntryController;
import com.ndominkiewicz.frontend.controller.component.ResultController;
import com.ndominkiewicz.frontend.model.*;
import com.ndominkiewicz.frontend.service.BipartiteService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.checkerframework.checker.units.qual.C;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TreeMap;


/**
 * @author Norbert Dominkiewicz
 */

public class BipartiteController implements ViewController {
    private final BipartiteService bipartiteService = new BipartiteService();
    private final Map<Component, ComponentController> components = new TreeMap<>();
    private Component currentComponent;
    /**
     * Controllers
     */
    private MainController mainController;
    /**
     * FXML Elements
     */
    @FXML private FlowPane componentsContainer;
    @FXML private GridPane root;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initActions();
        initComponents();
        swapComponent(Component.ENTRY);
    }
    private void initActions() {

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
        if(Objects.equals(entryController.getEquationField().getText(), "")){
            result = bipartiteService.calculate();
        } else {
            result = bipartiteService.calculate(entryController.getData());
        }
        ResultController resultController = (ResultController) components.get(Component.RESULT);
        resultController.updateLabels(result.mode(), result.iterations(), result.a(), result.b(), result.L(), result.x1(), result.x2(), result.xsr());
    }
}
