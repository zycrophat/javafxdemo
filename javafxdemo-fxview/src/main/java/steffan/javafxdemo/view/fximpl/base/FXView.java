package steffan.javafxdemo.view.fximpl.base;

import javafx.application.Platform;
import javafx.stage.Stage;
import steffan.javafxdemo.view.api.View;

public class FXView<T> implements View<T> {

    private Stage stage;

    private JavaFXSceneController<T> sceneController;

    FXView(Stage stage, JavaFXSceneController<T> sceneController) {
        this.stage = stage;
        this.sceneController = sceneController;
    }

    @Override
    public void show() {
        Platform.runLater(stage::show);
    }

    @Override
    public void hide() {
        Platform.runLater(stage::hide);
    }

    @Override
    public T getModel() {
        return sceneController.getModel();
    }

    @Override
    public void setModel(T model) {
        sceneController.setModel(model);
    }

    JavaFXSceneController<T> getSceneController() {
        return sceneController;
    }

    public Stage getStage() {
        return stage;
    }
}
