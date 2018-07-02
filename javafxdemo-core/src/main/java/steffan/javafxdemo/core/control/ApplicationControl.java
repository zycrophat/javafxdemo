package steffan.javafxdemo.core.control;

import steffan.javafxdemo.core.persistence.api.PersistenceContext;
import steffan.javafxdemo.core.view.api.UIViewManager;

public interface ApplicationControl {
    void initialize();

    void start();

    boolean isInitialized();

    UIViewManager getUiViewManager();

    PersistenceContext getPersistenceContext();

    CommandRunner getCommandRunner();
}
