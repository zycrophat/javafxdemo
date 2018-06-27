package steffan.javafxdemo.app.main;

import steffan.javafxdemo.commands.Command;
import steffan.javafxdemo.commands.CommandException;
import steffan.javafxdemo.control.CommandRunner;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class ExecutorServiceCommandRunner implements CommandRunner {

    private final ExecutorService executorService;

    ExecutorServiceCommandRunner(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public <T> Future<Optional<T>> executeCommand(Command<T> command, Consumer<CommandException> onCommandException) {
        return executeCommand(command, r -> {}, onCommandException);
    }

    @Override
    public <T> Future<Optional<T>> executeCommand(Command<T> command, Consumer<Optional<T>> onCompletion, Consumer<CommandException> onCommandException) {
        return executorService.submit(() -> {
            try {
                var result = command.run();
                onCompletion.accept(result);
                return result;
            } catch (CommandException e) {
                onCommandException.accept(e);
                return Optional.empty();
            }
        });
    }
}
