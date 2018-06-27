package steffan.javafxdemo.view.fximpl.base;

import javafx.application.Platform;

import java.util.concurrent.Semaphore;

class PlatformHelper {

    static void runLaterAndWait(Runnable runnable) {
        Semaphore semaphore = new Semaphore(0);

        Platform.runLater(() -> {
            runnable.run();
            semaphore.release();
        });

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
