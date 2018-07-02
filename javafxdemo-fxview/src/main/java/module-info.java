import steffan.javafxdemo.view.api.UIViewManager;
import steffan.javafxdemo.view.fximpl.base.FXUIViewManager;

module steffan.javafxdemo.fxview {
    requires steffan.javafxdemo.core;
    requires javafx.controls;
    requires javafx.fxml;

    exports steffan.javafxdemo.view.fximpl.base to javafx.graphics, javafx.fxml;
    exports steffan.javafxdemo.view.fximpl.contactlist to javafx.graphics, javafx.fxml;
    opens steffan.javafxdemo.view.fximpl.base to javafx.fxml;
    opens steffan.javafxdemo.view.fximpl.contactlist to javafx.fxml;

    provides UIViewManager with FXUIViewManager;
}
