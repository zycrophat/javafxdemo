package steffan.javafxdemo.view.fximpl;

import java.util.function.Consumer;

public abstract class JavaFXFormController<T> extends JavaFXSceneController<T> {

    private Consumer<T> onSubmit = (x) -> {};
    private Consumer<T> onCancel = (x) -> {};

    public void setOnSubmit(Consumer<T> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public void setOnCancel(Consumer<T> onSubmit) {
        this.onCancel = onSubmit;
    }

    public Consumer<T> getOnSubmit() {
        return onSubmit;
    }

    public Consumer<T> getOnCancel() {
        return onCancel;
    }
}
