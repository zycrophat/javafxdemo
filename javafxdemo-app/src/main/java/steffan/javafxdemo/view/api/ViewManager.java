package steffan.javafxdemo.view.api;

import steffan.javafxdemo.control.ApplicationControl;
import steffan.javafxdemo.models.domainmodel.ContactDTO;
import steffan.javafxdemo.models.viewmodel.ContactList;

public interface ViewManager {

    void initialize(ApplicationControl applicationControl) throws ViewException;

    View<ContactList> createContactsView() throws ViewException;

    Form<ContactDTO> createContactForm(ContactDTO contactDTO, String formTitle) throws ViewException;
}
