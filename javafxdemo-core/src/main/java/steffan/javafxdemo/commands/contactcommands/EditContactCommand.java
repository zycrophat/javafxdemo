package steffan.javafxdemo.commands.contactcommands;

import steffan.javafxdemo.commands.base.Command;
import steffan.javafxdemo.commands.base.CommandException;
import steffan.javafxdemo.control.ApplicationControl;
import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.models.domainmodel.ContactDTO;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.view.api.UIForm;
import steffan.javafxdemo.view.api.UIViewException;

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
            UIForm<ContactDTO> createContactUIForm =
                    applicationControl.getUiViewManager().createContactUIForm(contactDTO, "Edit Contact");

            createContactUIForm.setOnSubmit(c -> {

                contact.setFirstName(c.getFirstName());
                contact.setLastName(c.getLastName());

                contactList.setModified(true);
                applicationControl.getPersistenceContext().withUnitOfWork(unitOfWork -> {
                    unitOfWork.markAsModified(contact);
                });

                createContactUIForm.hide();
            });

            createContactUIForm.setOnCancel(c -> createContactUIForm.hide());

            createContactUIForm.showAndWait();
            return Optional.of(contact);
        } catch (UIViewException e) {
           throw new CommandException(e);
        }
    }
}
