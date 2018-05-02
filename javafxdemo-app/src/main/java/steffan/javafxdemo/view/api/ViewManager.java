package steffan.javafxdemo.view.api;

import javafx.collections.ObservableList;
import steffan.javafxdemo.app.domain.Contact;

public interface ViewManager {

    void initialize() throws ViewException;

    View<ObservableList<Contact>> createContactsView() throws ViewException;
}
