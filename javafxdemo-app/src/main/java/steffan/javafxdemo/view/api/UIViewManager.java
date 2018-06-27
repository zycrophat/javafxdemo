package steffan.javafxdemo.view.api;

import steffan.javafxdemo.control.ApplicationControl;
import steffan.javafxdemo.models.domainmodel.ContactDTO;
import steffan.javafxdemo.models.viewmodel.ContactList;

public interface UIViewManager {

    void initialize(ApplicationControl applicationControl) throws UIViewException;

    UIView<ContactList> createContactsView() throws UIViewException;

    UIForm<ContactDTO> createContactForm(ContactDTO contactDTO, String formTitle) throws UIViewException;
}
