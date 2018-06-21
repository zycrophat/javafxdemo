package steffan.javafxdemo.commands;

import steffan.javafxdemo.control.ApplicationControl;
import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.models.domainmodel.ContactDTO;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.view.api.Form;
import steffan.javafxdemo.view.api.ViewException;

import java.util.Optional;

public class EditContactCommand implements Command<Contact> {
    private final Contact contact;
    private final ApplicationControl applicationControl;
    private final ContactList contactList;

    public EditContactCommand(Contact contact, ContactList contactList, ApplicationControl applicationControl) {
        this.contact = contact;
        this.contactList = contactList;
        this.applicationControl = applicationControl;
    }

    @Override
    public Optional<Contact> run() throws CommandException {

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());

        try {
            Form<ContactDTO> createContactForm =
                    applicationControl.getViewManager().createContactForm(contactDTO, "Edit Contact");

            createContactForm.setOnSubmit(c -> {

                contact.setFirstName(c.getFirstName());
                contact.setLastName(c.getLastName());

                contactList.setModified(true);
                applicationControl.getPersistenceContext().withUnitOfWork(unitOfWork -> {
                    unitOfWork.markAsModified(contact);
                });

                createContactForm.hide();
            });

            createContactForm.setOnCancel(c -> createContactForm.hide());

            createContactForm.showAndWait();
            return Optional.of(contact);
        } catch (ViewException e) {
           throw new CommandException(e);
        }
    }
}
