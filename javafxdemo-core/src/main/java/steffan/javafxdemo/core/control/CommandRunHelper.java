package steffan.javafxdemo.core.control;

import steffan.javafxdemo.core.commands.base.Command;
import steffan.javafxdemo.core.commands.base.CommandException;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class CommandRunHelper<T> {

    private CommandRunner commandRunner;
    private Command<T> command;
    private Consumer<Optional<T>> completionHandler = o-> {};
    private Consumer<CommandException> onCommandException = e -> {};

    private CommandRunHelper(){

    }

    public static <T> CommandRunHelper<T> run(Command<T> command) {
        Objects.requireNonNull(command, "command must not be null");

        CommandRunHelper<T> commandRunHelper = new CommandRunHelper<>();
        commandRunHelper.command = command;
        return commandRunHelper;
    }

    public CommandRunHelper<T> using(CommandRunner commandRunner) {
        Objects.requireNonNull(commandRunner, "commandRunner must not be null");

        this.commandRunner = commandRunner;
        return this;
    }

    public CommandRunHelper<T> onCompletion(Consumer<Optional<T>> completionHandler) {
        this.completionHandler = Objects.requireNonNullElseGet(
                completionHandler, () -> o -> {});
        return this;
    }

    public CommandRunHelper<T> onCommandException(Consumer<CommandException> onCommandException) {
        this.onCommandException = Objects.requireNonNullElseGet(
                onCommandException, () -> e -> {});
        return this;
    }

    public Future<Optional<T>> execute() {
        Objects.requireNonNull(command, "Cannot execute (no CommandRunner)");
        return commandRunner.executeCommand(command, completionHandler, onCommandException);
    }
}