package com.ndominkiewicz.frontend.controller.component;

import com.ndominkiewicz.frontend.controller.view.BipartiteController;
import com.ndominkiewicz.frontend.controller.view.BisectionController;
import com.ndominkiewicz.frontend.model.ComponentController;
import com.ndominkiewicz.frontend.model.ViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EntryController implements ComponentController {
    ViewController viewController;
    @FXML private TextField equationField;
    @FXML private TextField epsilonField;
    @FXML private TextField aField;
    @FXML private TextField bField;
    @FXML private Button runButton;
    @FXML private GridPane root;
    public Node getView() {
        return root;
    }
    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }
    public String [] getData() {
        return new String[] {
                equationField.getText(),
                epsilonField.getText(),
                aField.getText(),
                bField.getText()
        };
    }
    public TextField getEquationField() {
        return equationField;
    }

    public void clearFields() {
        equationField.setText("");
        epsilonField.setText("");
        aField.setText("");
        bField.setText("");
    }
    public void initActions() {
        runButton.setOnAction(actionEvent -> {
            if(viewController instanceof BipartiteController bipartiteController) {
                bipartiteController.onCalculate();
            } else if(viewController instanceof BisectionController bisectionController) {
                bisectionController.onCalculate();
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initActions();
    }
}
