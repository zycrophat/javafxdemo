package steffan.javafxdemo.core.control;

import steffan.javafxdemo.core.commands.base.Command;
import steffan.javafxdemo.core.commands.base.CommandException;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface CommandRunner {
    <T> Future<Optional<T>> executeCommand(Command<T> command, Consumer<Optional<T>> completionHandler, Consumer<CommandException> onCommandException);
}
