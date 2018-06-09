package steffan.javafxdemo.domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.persistence.api.UnitOfWork;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ContactList {

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    private BooleanProperty modified = new SimpleBooleanProperty(false);

    private PersistenceContext persistenceContext;
    private UnitOfWork unitOfWork;

    public ContactList(PersistenceContext persistenceContext) {
        this.persistenceContext = persistenceContext;
    }

    public void load() throws PersistenceException {
        var repository = persistenceContext.getRepository(Contact.class).get();

        contacts.setAll(StreamSupport.stream(repository.find().spliterator(), false).collect(Collectors.toList()));

        setModified(false);
        unitOfWork = null;
    }

    public void save() throws PersistenceException {
        getUnitOfWork().commit(persistenceContext);
        modified.set(false);
    }

    public ObservableList<Contact> getContacts() {
        return FXCollections.observableList(contacts);
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

    public UnitOfWork getUnitOfWork() {
        if (unitOfWork == null || unitOfWork.isCommitted()) {
            unitOfWork = persistenceContext.createUnitOfWork();
        }
        return unitOfWork;
    }

}
