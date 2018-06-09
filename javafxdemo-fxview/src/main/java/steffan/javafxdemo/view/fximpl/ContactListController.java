package steffan.javafxdemo.view.fximpl;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.domain.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.persistence.api.UnitOfWork;

public class ContactListController extends JavaFXSceneController<ContactList> {

    @FXML
    private ListView<Contact> contactsListView;

    @FXML
    private Button saveButton;
    private UnitOfWork unitOfWork;

    @Override
    protected void initialize(ContactList model) {
        saveButton.disableProperty().bind(model.modifiedProperty().not());

        unitOfWork = getPersistenceContext().createUnitOfWork();
        contactsListView.setCellFactory(
                listView -> new ObserveAndEditListCell<>(
                        contact ->
                                contact.idProperty().asString().
                                concat(": ").
                                concat(contact.firstNameProperty()).
                                concat(" ").
                                concat(contact.lastNameProperty()),

                        contact ->
                                contact.getFirstName() + ((contact.getLastName().isEmpty()) ? "" : " " + contact.getLastName()),

                        (contact, text) -> {
                                var names = text.split(" ", 2);
                                contact.setFirstName(names[0]);

                                unitOfWork.markAsModified(contact);

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
            getPersistenceContext().doInTransaction(ctx -> {
                unitOfWork.commit();
                unitOfWork = ctx.createUnitOfWork();
            });
            getModel().setModified(false);
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
