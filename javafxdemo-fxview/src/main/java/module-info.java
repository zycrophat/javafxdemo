import steffan.javafxdemo.view.api.ViewManager;
import steffan.javafxdemo.view.fximpl.base.FXViewManager;

module steffan.javafxdemo.fxview {
    requires steffan.javafxdemo.app;
    requires javafx.controls;
    requires javafx.fxml;

    exports steffan.javafxdemo.view.fximpl.base to javafx.graphics, javafx.fxml;
    exports steffan.javafxdemo.view.fximpl.contactlist to javafx.graphics, javafx.fxml;
    opens steffan.javafxdemo.view.fximpl.base to javafx.fxml;
    opens steffan.javafxdemo.view.fximpl.contactlist to javafx.fxml;

    provides ViewManager with FXViewManager;
}
