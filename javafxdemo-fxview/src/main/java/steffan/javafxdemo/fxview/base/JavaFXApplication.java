package steffan.javafxdemo.fxview.base;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

public class JavaFXApplication extends Application {

    private static FXUIViewManager fxViewManager;

    private static final CountDownLatch initCountDownLatch = new CountDownLatch(1);

    public static void initialize(FXUIViewManager fxViewManager) {
        JavaFXApplication.fxViewManager = fxViewManager;
        new Thread(Application::launch, "JavaFXApplication").start();
        try {
            initCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxViewManager.setPrimaryStage(primaryStage);
        initCountDownLatch.countDown();
    }
}
