package steffan.javafxdemo.view.api;

import javafx.collections.ObservableList;
import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.domain.ContactList;

public interface ViewManager {

    void initialize() throws ViewException;

    View<ContactList> createContactsView() throws ViewException;
}
