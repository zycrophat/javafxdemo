package steffan.javafxdemo.view.fximpl;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.domain.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceException;

public class ContactListController extends JavaFXSceneController<ContactList> {

    @FXML
    private ListView<Contact> contactsListView;

    @FXML
    private Button saveButton;

    @Override
    protected void bindModelToElements(ContactList model) {
        saveButton.disableProperty().bind(model.modifiedProperty().not());

        contactsListView.setCellFactory(
                listView -> new ObserveAndEditListCell<>(
                        (Contact c) ->
                                c.idProperty().asString().
                                concat(": ").
                                concat(c.firstNameProperty()).
                                concat(" ").
                                concat(c.lastNameProperty()),
                        c ->
                                c.getFirstName() + ((c.getLastName().isEmpty()) ? "" : " " + c.getLastName()),
                        (c, text) -> {
                                var names = text.split(" ", 2);
                                var contact = listView.getSelectionModel().getSelectedItem();
                                contact.setFirstName(names[0]);

                                if (names.length > 1) {
                                    contact.setLastName(names[1]);
                                } else {
                                    contact.setLastName("");
                                }
                                model.setModified(true);
                                return contact;
                        }
                )
        );
        contactsListView.setEditable(true);
        contactsListView.setItems(model.getContacts());
    }

    @FXML
    private void saveContactList() {
        try {
            getModel().save();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadContactList() {
        try {
            getModel().load();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
