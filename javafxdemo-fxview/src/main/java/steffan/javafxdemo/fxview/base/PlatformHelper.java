package steffan.javafxdemo.fxview.base;

import javafx.application.Platform;
import steffan.javafxdemo.core.view.api.UIViewException;

import java.util.concurrent.*;

class PlatformHelper {

    static void runLaterAndWait(Runnable runnable) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Platform.runLater(() -> {
            runnable.run();
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static <T> T callLater(Callable<T> callable) throws UIViewException {
        CompletableFuture<T> future = new CompletableFuture<>();

        Platform.runLater(() -> {
            try {
                T result = callable.call();
                future.complete(result);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new UIViewException(e);
        }
    }
}
