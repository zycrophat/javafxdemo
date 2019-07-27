package steffan.javafxdemo.core.view.api;

import javafx.beans.property.Property;
import steffan.javafxdemo.core.control.ApplicationControl;
import steffan.javafxdemo.core.models.domainmodel.ContactDTO;
import steffan.javafxdemo.core.models.viewmodel.ContactList;

import java.util.function.Function;

public interface UIViewManager {

    void initialize(ApplicationControl applicationControl) throws UIViewException;

    UIView<ContactList> createContactsUIView() throws UIViewException;

    UIForm<ContactDTO> createContactUIForm(ContactDTO contactDTO, String formTitle) throws UIViewException;

    default Function<Property<?>, Property<?>> getProxyProvider() {
        return p -> p;
    }
}
