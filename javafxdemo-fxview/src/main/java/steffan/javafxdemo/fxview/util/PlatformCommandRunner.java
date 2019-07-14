package steffan.javafxdemo.fxview.util;

import javafx.application.Platform;
import steffan.javafxdemo.core.commands.base.Command;
import steffan.javafxdemo.core.commands.base.CommandException;
import steffan.javafxdemo.core.control.CommandRunner;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class PlatformCommandRunner implements CommandRunner {

    @Override
    public <T> Future<Optional<T>> executeCommand(Command<T> command, Consumer<Optional<T>> completionHandler, Consumer<CommandException> onCommandException) {
        CompletableFuture<Optional<T>> future = new CompletableFuture<>();
        Platform.runLater(() -> {
            try {
                var result = command.run();
                completionHandler.accept(result);
                future.complete(result);
            } catch (CommandException e) {
                future.completeExceptionally(e);
                onCommandException.accept(e);
            }
        });

        return future;
    }
}
