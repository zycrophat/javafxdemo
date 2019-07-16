package steffan.javafxdemo.core.control;

import javafx.application.Platform;
import steffan.javafxdemo.core.view.api.UIViewException;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class PlatformHelper {

    public static void runLaterOrOnPlatformThreadAndWait(Runnable runnable) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        runLaterOrOnPlatformThread(() -> {
            runnable.run();
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void runLaterOrOnPlatformThread(Runnable runnable) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(runnable);
        } else {
            runnable.run();
        }
    }

    public static <T> T callLater(Callable<T> callable) throws UIViewException {
        CompletableFuture<T> future = new CompletableFuture<>();

        runLaterOrOnPlatformThread(() -> {
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
