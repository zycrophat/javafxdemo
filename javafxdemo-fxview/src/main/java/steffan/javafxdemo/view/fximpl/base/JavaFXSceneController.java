package steffan.javafxdemo.view.fximpl.base;

import steffan.javafxdemo.persistence.api.PersistenceContext;

public abstract class JavaFXSceneController<T> {

    private PersistenceContext persistenceContext;
    private T model;

    private FXViewManager fxViewManager;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;

        initialize(model);
    }

    public FXViewManager getFxViewManager() {
        return fxViewManager;
    }

    public void setFxViewManager(FXViewManager fxViewManager) {
        this.fxViewManager = fxViewManager;
    }

    public PersistenceContext getPersistenceContext() {
        return persistenceContext;
    }

    public void setPersistenceContext(PersistenceContext persistenceContext) {
        this.persistenceContext = persistenceContext;
    }

    public void configure(FXViewManager fxViewManager, PersistenceContext persistenceContext) {
        setFxViewManager(fxViewManager);
        setPersistenceContext(persistenceContext);
    }

    protected abstract void initialize(T model);
}
