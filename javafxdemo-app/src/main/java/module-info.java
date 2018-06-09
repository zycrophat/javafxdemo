import steffan.javafxdemo.view.api.ViewManager;

module steffan.javafxdemo.app {

    requires transitive javafx.base;

    exports steffan.javafxdemo.view.api;
    exports steffan.javafxdemo.domain;
    exports steffan.javafxdemo.persistence.api;
    exports steffan.javafxdemo.app.main;

    uses ViewManager;
}