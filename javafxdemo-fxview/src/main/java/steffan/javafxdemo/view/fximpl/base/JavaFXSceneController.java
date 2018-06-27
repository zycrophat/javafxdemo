package steffan.javafxdemo.view.fximpl.base;

import steffan.javafxdemo.control.ApplicationControl;

public abstract class JavaFXSceneController<T> {

    private ApplicationControl applicationControl;
    private T model;

    private FXUIViewManager fxViewManager;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;

        initialize(model);
    }

    public FXUIViewManager getFxViewManager() {
        return fxViewManager;
    }

    public void setFxViewManager(FXUIViewManager fxViewManager) {
        this.fxViewManager = fxViewManager;
    }

    public ApplicationControl getApplicationControl() {
        return applicationControl;
    }

    public void setApplicationControl(ApplicationControl applicationControl) {
        this.applicationControl = applicationControl;
    }

    public void configure(FXUIViewManager fxViewManager, ApplicationControl applicationControl) {
        setFxViewManager(fxViewManager);
        setApplicationControl(applicationControl);
    }

    protected abstract void initialize(T model);
}
