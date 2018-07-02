package steffan.javafxdemo.core.view.api;

import java.util.function.Consumer;

public interface UIForm<T> extends UIView<T> {

    void setOnSubmit(Consumer<T> onSubmit);

    void setOnCancel(Consumer<T> onCancel);

    void showAndWait();
}
