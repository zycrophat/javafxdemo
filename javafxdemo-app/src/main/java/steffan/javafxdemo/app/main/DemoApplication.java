package steffan.javafxdemo.app.main;

import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.view.api.ViewManager;

public interface DemoApplication {
    void initialize();

    void start();

    boolean isInitialized();

    ViewManager getViewManager();

    PersistenceContext getPersistenceContext();
}
