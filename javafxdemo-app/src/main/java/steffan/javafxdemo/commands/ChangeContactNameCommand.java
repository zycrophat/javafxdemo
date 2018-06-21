package steffan.javafxdemo.commands;

import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceContext;

public class ChangeContactNameCommand implements Command<Contact> {
    private final PersistenceContext persistenceContext;
    private final Contact contact;
    private final String firstName;
    private final String lastName;
    private final ContactList model;

    public ChangeContactNameCommand(Contact contact, String firstName, String lastName, ContactList model, PersistenceContext persistenceContext) {
        this.contact = contact;
        this.firstName = firstName;
        this.lastName = lastName;
        this.persistenceContext = persistenceContext;
        this.model = model;
    }

    @Override
    public Contact run() {
        contact.setFirstName(firstName);
        contact.setLastName(lastName);

        persistenceContext.withUnitOfWork(unitOfWork -> unitOfWork.markAsModified(contact));

        model.setModified(true);

        return contact;
    }
}
