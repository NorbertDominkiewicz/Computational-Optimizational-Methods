package com.ndominkiewicz.frontend.controller.component;

import com.ndominkiewicz.frontend.controller.view.BipartiteController;
import com.ndominkiewicz.frontend.model.Component;
import com.ndominkiewicz.frontend.model.ComponentController;
import com.ndominkiewicz.frontend.model.ViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements ComponentController {
    private ViewController viewController;
    /**
     * FXML Labels
     */
    @FXML private Label aLabel;
    @FXML private Label bLabel;
    @FXML private Label extremeLabel;
    @FXML private Label iterationsLabel;
    @FXML private Label lLabel;
    @FXML private Label x1Label;
    @FXML private Label x2Label;
    @FXML private Label xsrLabel;
    //
    @FXML private VBox resultContainer;
    @FXML private GridPane root;
    @FXML private Button returnButton;
    public Node getView() {
        return root;
    }
    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }
    public void clearLabels() {
        extremeLabel.setText("");
        iterationsLabel.setText("");
        aLabel.setText("");
        bLabel.setText("");
        lLabel.setText("");
        x1Label.setText("");
        x2Label.setText("");
        xsrLabel.setText("");
    }
    public void updateLabels(String extreme, double iterations, double a, double b, double L, double x1, double x2, double xsr) {
        extremeLabel.setText(extreme);
        iterationsLabel.setText(String.valueOf(iterations));
        aLabel.setText(String.valueOf(a));
        bLabel.setText(String.valueOf(b));
        lLabel.setText(String.valueOf(L));
        x1Label.setText(String.valueOf(x1));
        x2Label.setText(String.valueOf(x2));
        xsrLabel.setText(String.valueOf(xsr));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initActions();
    }
    private void initActions() {
        returnButton.setOnAction(actionEvent -> {
            if(viewController instanceof BipartiteController bipartiteController) {
                bipartiteController.swapComponent(Component.ENTRY);
            }
        });
    }
}
