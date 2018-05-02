package steffan.javafxdemo.view.fximpl;

public abstract class JavaFXSceneController<T> {

    private T model;

    private FXViewManager fxViewManager;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;

        bindModelToElements(model);
    }

    public FXViewManager getFxViewManager() {
        return fxViewManager;
    }

    public void setFxViewManager(FXViewManager fxViewManager) {
        this.fxViewManager = fxViewManager;
    }

    protected abstract void bindModelToElements(T model);
}
