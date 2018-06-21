package steffan.javafxdemo.view.fximpl.base;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class JavaFXApplication extends Application {

    private static FXViewManager fxViewManager;

    public static void initialize(FXViewManager fxViewManager) {
        JavaFXApplication.fxViewManager = fxViewManager;
        new Thread(Application::launch).start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxViewManager.setPrimaryStage(primaryStage);
    }
}
