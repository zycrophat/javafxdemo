package steffan.javafxdemo.core.commands.contactcommands;

import steffan.javafxdemo.core.commands.base.Command;
import steffan.javafxdemo.core.commands.base.CommandException;
import steffan.javafxdemo.core.control.ApplicationControl;
import steffan.javafxdemo.core.models.domainmodel.Contact;
import steffan.javafxdemo.core.models.domainmodel.ContactDTO;
import steffan.javafxdemo.core.view.api.UIForm;
import steffan.javafxdemo.core.view.api.UIViewException;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class EditContactCommand implements Command<Contact> {

    private final Contact contact;
    private final ApplicationControl applicationControl;

    public EditContactCommand(Contact contact, ApplicationControl applicationControl) {
        this.contact = contact;
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

            AtomicReference<Contact> editedContact = new AtomicReference<>();
            createContactUIForm.setOnSubmit(c -> {

                contact.setFirstName(c.getFirstName());
                contact.setLastName(c.getLastName());

                editedContact.set(contact);
                applicationControl.getPersistenceContext().withUnitOfWork(
                        unitOfWork -> unitOfWork.markAsModified(contact)
                );

                createContactUIForm.hide();
            });

            createContactUIForm.setOnCancel(c -> createContactUIForm.hide());

            createContactUIForm.showAndWait();
            return Optional.ofNullable(editedContact.get());
        } catch (UIViewException e) {
           throw new CommandException(e);
        }
    }
}
