import steffan.javafxdemo.core.view.api.UIViewManager;
import steffan.javafxdemo.fxview.base.FXUIViewManager;

module steffan.javafxdemo.fxview {
    requires steffan.javafxdemo.core;
    requires javafx.controls;
    requires javafx.fxml;

    exports steffan.javafxdemo.fxview.base to javafx.graphics, javafx.fxml;
    exports steffan.javafxdemo.fxview.contactlist to javafx.graphics, javafx.fxml;
    opens steffan.javafxdemo.fxview.base to javafx.fxml;
    opens steffan.javafxdemo.fxview.contactlist to javafx.fxml;

    provides UIViewManager with FXUIViewManager;
}
