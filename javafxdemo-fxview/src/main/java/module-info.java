import steffan.javafxdemo.view.api.ViewManager;
import steffan.javafxdemo.view.fximpl.FXViewManager;

module steffan.javafxdemo.fxview {
    requires steffan.javafxdemo.app;
    requires javafx.controls;
    requires javafx.fxml;

    exports steffan.javafxdemo.view.fximpl to javafx.graphics, javafx.fxml;
    opens steffan.javafxdemo.view.fximpl to javafx.fxml;

    provides ViewManager with FXViewManager;
}