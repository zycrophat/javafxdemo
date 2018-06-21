import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.view.api.ViewManager;

module steffan.javafxdemo.app {

    requires transitive javafx.base;

    exports steffan.javafxdemo.view.api;
    exports steffan.javafxdemo.models.domainmodel;
    exports steffan.javafxdemo.persistence.api;
    exports steffan.javafxdemo;
    exports steffan.javafxdemo.models.viewmodel;

    uses ViewManager;
    uses PersistenceContext;
}