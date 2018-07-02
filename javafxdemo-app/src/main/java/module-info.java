import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.view.api.UIViewManager;

module steffan.javafxdemo.app {
    requires steffan.javafxdemo.core;

    uses UIViewManager;
    uses PersistenceContext;
}