package steffan.javafxdemo.control;

import steffan.javafxdemo.commands.Command;
import steffan.javafxdemo.commands.CommandException;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface CommandRunner {

    <T> Future<Optional<T>> executeCommand(Command<T> command, Consumer<CommandException> onCommandException);
}
