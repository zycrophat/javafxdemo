package steffan.javafxdemo.view.fximpl.base;

import javafx.application.Platform;
import javafx.stage.Stage;
import steffan.javafxdemo.view.api.Form;

import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

import static steffan.javafxdemo.view.fximpl.base.PlatformHelper.runLaterAndWait;

public class FXForm<T> extends FXView<T> implements Form<T> {

    private JavaFXFormController<T> formController;

    FXForm(Stage stage, JavaFXFormController<T> formController) {
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

    @Override
    public void showAndWait() {
        runLaterAndWait(getStage()::showAndWait);
    }
}
