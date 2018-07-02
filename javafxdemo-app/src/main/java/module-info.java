import steffan.javafxdemo.core.persistence.api.PersistenceContext;
import steffan.javafxdemo.core.view.api.UIViewManager;

module steffan.javafxdemo.app {
    requires steffan.javafxdemo.core;

    uses UIViewManager;
    uses PersistenceContext;
}