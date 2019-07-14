package steffan.javafxdemo.fxview.base;

import javafx.stage.Stage;
import steffan.javafxdemo.core.view.api.UIForm;
import steffan.javafxdemo.fxview.util.PlatformHelper;

import java.util.function.Consumer;

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
        PlatformHelper.runLaterAndWait(getStage()::showAndWait);
    }
}
