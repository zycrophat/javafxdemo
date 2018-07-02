package steffan.javafxdemo.core.view.api;

public interface UIView<T> {

    void setModel(T model);

    T getModel();

    void show();

    void hide();
}
