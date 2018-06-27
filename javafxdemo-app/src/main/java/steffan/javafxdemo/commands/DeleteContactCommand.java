package steffan.javafxdemo.commands;

import steffan.javafxdemo.control.ApplicationControl;
import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.models.domainmodel.ContactDTO;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.view.api.Form;
import steffan.javafxdemo.view.api.ViewException;

import java.util.Optional;

public class DeleteContactCommand implements Command<Contact> {

    private final Contact contact;
    private final ApplicationControl applicationControl;

    public DeleteContactCommand(Contact contact, ApplicationControl applicationControl) {
        this.contact = contact;
        this.applicationControl = applicationControl;
    }

    @Override
    public Optional<Contact> run() {
        applicationControl.getPersistenceContext().withUnitOfWork(unitOfWork -> {
            unitOfWork.markAsDeleted(contact);
        });
        return Optional.of(contact);
    }
}
