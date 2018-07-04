package steffan.javafxdemo.core.commands.base;

import java.util.Optional;

@FunctionalInterface
public interface Command<T> {
    Optional<T> run() throws CommandException;
}
