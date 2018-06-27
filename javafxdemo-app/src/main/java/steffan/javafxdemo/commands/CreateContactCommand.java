package steffan.javafxdemo.commands;

import steffan.javafxdemo.control.ApplicationControl;
import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.models.domainmodel.ContactDTO;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.view.api.ViewException;

import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

public class CreateContactCommand implements Command<Contact> {

    private final ApplicationControl applicationControl;

    public CreateContactCommand(ApplicationControl applicationControl) {
        this.applicationControl = requireNonNull(applicationControl);
    }

    @Override
    public Optional<Contact> run() throws CommandException {

        var viewManager = applicationControl.getViewManager();
        var persistenceCtx = applicationControl.getPersistenceContext();
        AtomicReference<Contact> contactAtomicReference = new AtomicReference<>();

        try {
            var createContactForm = viewManager.createContactForm(new ContactDTO(), "Create Contact");

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
        } catch (ViewException e) {
            e.printStackTrace();
            throw new CommandException(e);
        }

        Contact contact = contactAtomicReference.get();
        return Optional.ofNullable(contact);
    }
}
