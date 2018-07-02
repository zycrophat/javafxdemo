package steffan.javafxdemo.fxview.base;

import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXApplication extends Application {

    private static FXUIViewManager fxViewManager;

    public static void initialize(FXUIViewManager fxViewManager) {
        JavaFXApplication.fxViewManager = fxViewManager;
        new Thread(Application::launch).start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxViewManager.setPrimaryStage(primaryStage);
    }
}
