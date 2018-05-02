package steffan.javafxdemo.view.api;

public interface View<T> {

    void setModel(T model);

    T getModel();

    void show();

    void hide();
}
