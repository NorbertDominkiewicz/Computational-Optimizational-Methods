module com.ndominkiewicz.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires exp4j;

    opens com.ndominkiewicz.frontend to javafx.fxml;
    exports com.ndominkiewicz.frontend;
    opens com.ndominkiewicz.frontend.controller.view to javafx.fxml;
    exports com.ndominkiewicz.frontend.controller.view;
    opens com.ndominkiewicz.frontend.controller.component to javafx.fxml;
    exports com.ndominkiewicz.frontend.controller.component;
}