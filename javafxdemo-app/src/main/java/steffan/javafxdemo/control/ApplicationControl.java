package steffan.javafxdemo.control;

import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.view.api.UIViewManager;

public interface ApplicationControl {
    void initialize();

    void start();

    boolean isInitialized();

    UIViewManager getUiViewManager();

    PersistenceContext getPersistenceContext();

    CommandRunner getCommandRunner();
}
