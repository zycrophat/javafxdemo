package steffan.javafxdemo.core.commands.base;

import java.util.Optional;

public interface Command<T> {
    Optional<T> run() throws CommandException;
}
