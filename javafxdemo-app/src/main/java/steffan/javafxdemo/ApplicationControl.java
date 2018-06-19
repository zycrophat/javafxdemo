package steffan.javafxdemo;

import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.view.api.ViewManager;

public interface ApplicationControl {
    void initialize();

    void start();

    boolean isInitialized();

    ViewManager getViewManager();

    PersistenceContext getPersistenceContext();
}