import steffan.javafxdemo.app.api.view.View;
import steffan.javafxdemo.app.api.view.fximpl.FXView;

module steffan.javafxdemo.fxview {
    requires steffan.javafxdemo.app;
    provides View with FXView;
}
