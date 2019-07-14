package steffan.javafxdemo.core.commands.contactcommands;

import steffan.javafxdemo.core.commands.base.Command;
import steffan.javafxdemo.core.control.ApplicationControl;
import steffan.javafxdemo.core.models.domainmodel.Contact;

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
        applicationControl.getPersistenceContext().withUnitOfWork(unitOfWork -> unitOfWork.markAsDeleted(contact));
        try {
            Thread.sleep(2500L); // artificially slowing down operation for GUI demonstration purposes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Optional.of(contact);
    }
}
