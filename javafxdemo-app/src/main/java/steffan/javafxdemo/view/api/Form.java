package steffan.javafxdemo.view.api;

import java.util.function.Consumer;

public interface Form<T> extends View<T>{

    void setOnSubmit(Consumer<T> onSubmit);

    void setOnCancel(Consumer<T> onCancel);
}
