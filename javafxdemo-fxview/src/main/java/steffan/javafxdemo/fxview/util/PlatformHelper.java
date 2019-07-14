package steffan.javafxdemo.fxview.util;

import javafx.application.Platform;
import steffan.javafxdemo.core.control.CommandRunner;
import steffan.javafxdemo.core.view.api.UIViewException;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class PlatformHelper {

    private static CommandRunner platformCommandRunner = new PlatformCommandRunner();

    public static void runLaterAndWait(Runnable runnable) {
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

    public static <T> T callLater(Callable<T> callable) throws UIViewException {
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

    public static CommandRunner getPlatformCommandRunner() {
        return platformCommandRunner;
    }
}
