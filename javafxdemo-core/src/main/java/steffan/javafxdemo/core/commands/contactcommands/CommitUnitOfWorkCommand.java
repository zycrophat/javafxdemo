package steffan.javafxdemo.core.commands.contactcommands;

import steffan.javafxdemo.core.commands.base.Command;
import steffan.javafxdemo.core.commands.base.CommandException;
import steffan.javafxdemo.core.control.ApplicationControl;
import steffan.javafxdemo.core.persistence.api.PersistenceException;

import java.util.Optional;

public class CommitUnitOfWorkCommand implements Command<Void> {

    private final ApplicationControl applicationControl;

    public CommitUnitOfWorkCommand(ApplicationControl applicationControl) {
        this.applicationControl = applicationControl;
    }

    @Override
    public Optional<Void> run() throws CommandException {
        try {
            applicationControl.getPersistenceContext().withUnitOfWorkInTransaction(
                    (ctx, unitOfWork) -> unitOfWork.commit(ctx)
            );
        } catch (PersistenceException e) {
            throw new CommandException(e);
        }
        return Optional.empty();
    }
}
