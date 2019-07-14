package steffan.javafxdemo.core.commands.contactcommands;

import steffan.javafxdemo.core.commands.base.Command;
import steffan.javafxdemo.core.commands.base.CommandException;
import steffan.javafxdemo.core.control.ApplicationControl;
import steffan.javafxdemo.core.models.domainmodel.Contact;
import steffan.javafxdemo.core.persistence.api.PersistenceException;

import java.util.List;
import java.util.Optional;

public class LoadContactsCommand implements Command<List<Contact>> {

    private final ApplicationControl applicationControl;

    public LoadContactsCommand(ApplicationControl applicationControl) {
        this.applicationControl = applicationControl;
    }

    @Override
    public Optional<List<Contact>> run() throws CommandException {
        try {
            return applicationControl.getPersistenceContext().doInTransaction(ctx -> {
                var contacts = ctx.getRepository(Contact.class).find();
                return Optional.of(contacts);
            });
        } catch (PersistenceException e) {
            throw new CommandException(e);
        }
    }
}
