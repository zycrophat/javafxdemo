package steffan.javafxdemo.view.api;

import steffan.javafxdemo.app.main.DemoApplication;
import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.domain.ContactList;

public interface ViewManager {

    void initialize(DemoApplication demoApplication) throws ViewException;

    View<ContactList> createContactsView() throws ViewException;

    Form<Contact> createCreateContactForm(Contact contact) throws ViewException;
}
