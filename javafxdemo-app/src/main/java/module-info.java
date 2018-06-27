import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.view.api.UIViewManager;

module steffan.javafxdemo.app {

    requires transitive javafx.base;

    exports steffan.javafxdemo.view.api;
    exports steffan.javafxdemo.models.domainmodel;
    exports steffan.javafxdemo.persistence.api;
    exports steffan.javafxdemo.control;
    exports steffan.javafxdemo.models.viewmodel;
    exports steffan.javafxdemo.commands.base;
    exports steffan.javafxdemo.commands.contactcommands;

    uses UIViewManager;
    uses PersistenceContext;
}