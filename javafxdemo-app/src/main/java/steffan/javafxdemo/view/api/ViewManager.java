package steffan.javafxdemo.view.api;

import steffan.javafxdemo.ApplicationControl;
import steffan.javafxdemo.domain.ContactDTO;
import steffan.javafxdemo.domain.ContactList;

public interface ViewManager {

    void initialize(ApplicationControl applicationControl) throws ViewException;

    View<ContactList> createContactsView() throws ViewException;

    Form<ContactDTO> createContactForm(ContactDTO contactDTO, String formTitle) throws ViewException;
}
