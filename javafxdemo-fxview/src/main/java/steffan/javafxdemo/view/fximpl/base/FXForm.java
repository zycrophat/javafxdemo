package steffan.javafxdemo.view.fximpl.base;

import javafx.stage.Stage;
import steffan.javafxdemo.view.api.Form;

import java.util.function.Consumer;

public class FXForm<T> extends FXView<T> implements Form<T> {

    private JavaFXFormController<T> formController;

    public FXForm(Stage stage, JavaFXFormController<T> formController) {
        super(stage, formController);
        this.formController = formController;
    }

    @Override
    public void setOnSubmit(Consumer<T> onSubmit) {
        formController.setOnSubmit(onSubmit);
    }

    @Override
    public void setOnCancel(Consumer<T> onCancel) {
        formController.setOnCancel(onCancel);
    }
}
