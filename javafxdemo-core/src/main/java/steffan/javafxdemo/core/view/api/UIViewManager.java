package steffan.javafxdemo.core.view.api;

import steffan.javafxdemo.core.control.ApplicationControl;
import steffan.javafxdemo.core.models.domainmodel.ContactDTO;
import steffan.javafxdemo.core.models.viewmodel.ContactList;

public interface UIViewManager {

    void initialize(ApplicationControl applicationControl) throws UIViewException;

    UIView<ContactList> createContactsUIView() throws UIViewException;

    UIForm<ContactDTO> createContactUIForm(ContactDTO contactDTO, String formTitle) throws UIViewException;
}
