package steffan.javafxdemo.view.fximpl.base;

import javafx.stage.Stage;
import steffan.javafxdemo.view.api.UIForm;

import java.util.function.Consumer;

import static steffan.javafxdemo.view.fximpl.base.PlatformHelper.runLaterAndWait;

public class FXUIForm<T> extends FXUIView<T> implements UIForm<T> {

    private JavaFXFormController<T> formController;

    FXUIForm(Stage stage, JavaFXFormController<T> formController) {
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
