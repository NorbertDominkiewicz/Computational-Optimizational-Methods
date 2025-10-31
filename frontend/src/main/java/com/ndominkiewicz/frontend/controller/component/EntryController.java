package com.ndominkiewicz.frontend.controller.component;

import com.ndominkiewicz.frontend.controller.view.BipartiteController;
import com.ndominkiewicz.frontend.model.ViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EntryController implements Initializable {
    ViewController controller;
    @FXML
    private TextField a;
    @FXML
    private TextField b;
    @FXML
    private TextField epsilon;
    @FXML
    private TextField task;
    @FXML
    private Button run;
    public String getA() {
        return a.getText();
    }
    public String getB() {
        return b.getText();
    }
    public String getEpsilon() {
        return epsilon.getText();
    }
    public String getEquation() {
        return task.getText();
    }
    public void setController(ViewController controller) {
        this.controller = controller;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        run.setOnAction(actionEvent -> {
            try {
                controller.onCalculate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
