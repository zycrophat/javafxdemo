package steffan.javafxdemo.core.commands.contactcommands;

import steffan.javafxdemo.core.commands.base.Command;
import steffan.javafxdemo.core.commands.base.CommandException;
import steffan.javafxdemo.core.control.ApplicationControl;
import steffan.javafxdemo.core.models.domainmodel.Contact;
import steffan.javafxdemo.core.models.domainmodel.ContactDTO;
import steffan.javafxdemo.core.view.api.UIViewException;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

public class CreateContactCommand implements Command<Contact> {

    private final ApplicationControl applicationControl;

    public CreateContactCommand(ApplicationControl applicationControl) {
        this.applicationControl = requireNonNull(applicationControl);
    }

    @Override
    public Optional<Contact> run() throws CommandException {

        var viewManager = applicationControl.getUiViewManager();
        var persistenceCtx = applicationControl.getPersistenceContext();
        AtomicReference<Contact> contactAtomicReference = new AtomicReference<>();

        try {
            var createContactForm = viewManager.createContactUIForm(new ContactDTO(), "Create Contact");

            createContactForm.setOnSubmit(c -> {
                Contact contact = new Contact(c.getId(), c.getFirstName(), c.getLastName());

                persistenceCtx.withUnitOfWork(unitOfWork -> {
                    unitOfWork.markAsNew(contact);
                });

                contactAtomicReference.set(contact);
                createContactForm.hide();
            });

            createContactForm.setOnCancel(c -> {
                createContactForm.hide();
            });

            createContactForm.showAndWait();
        } catch (UIViewException e) {
            e.printStackTrace();
            throw new CommandException(e);
        }

        Contact contact = contactAtomicReference.get();
        return Optional.ofNullable(contact);
    }
}
