package steffan.javafxdemo.models.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import steffan.javafxdemo.models.domainmodel.Contact;

import java.util.List;

public class ContactList {

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    private BooleanProperty modified = new SimpleBooleanProperty(false);

    public ContactList(List<Contact> contactList) {
        contacts.setAll(contactList);

        setModified(false);
    }

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public ReadOnlyBooleanProperty modifiedProperty() {
        return modified;
    }

    public boolean isModified() {
        return modified.get();
    }

    public void setModified(boolean modified) {
        this.modified.set(modified);
    }

    public void setContacts(ObservableList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void removeContact(Contact contact) {
        this.contacts.remove(contact);
    }
}
