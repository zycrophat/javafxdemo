package steffan.javafxdemo.commands;

import java.util.Optional;

public interface Command<T> {
    Optional<T> run() throws CommandException;
}
