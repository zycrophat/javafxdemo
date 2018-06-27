package steffan.javafxdemo.view.fximpl.base;

import javafx.application.Platform;
import steffan.javafxdemo.view.api.UIViewException;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
