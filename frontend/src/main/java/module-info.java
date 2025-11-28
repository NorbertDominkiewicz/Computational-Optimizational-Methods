module com.ndominkiewicz.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires exp4j;
    requires org.checkerframework.checker.qual;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;

    opens com.ndominkiewicz.frontend to javafx.fxml;
    exports com.ndominkiewicz.frontend;
    opens com.ndominkiewicz.frontend.service.severalVariables to javafx.fxml;
    exports com.ndominkiewicz.frontend.service.severalVariables;
    opens com.ndominkiewicz.frontend.controller.view to javafx.fxml;
    exports com.ndominkiewicz.frontend.controller.view;
    exports com.ndominkiewicz.frontend.controller;
    opens com.ndominkiewicz.frontend.controller to javafx.fxml;
    opens com.ndominkiewicz.frontend.controller.component to javafx.fxml;
    exports com.ndominkiewicz.frontend.controller.component;
    opens com.ndominkiewicz.frontend.model to com.fasterxml.jackson.databind;
    opens com.ndominkiewicz.frontend.utils to com.fasterxml.jackson.databind;
    opens com.ndominkiewicz.frontend.result to com.fasterxml.jackson.databind;
}