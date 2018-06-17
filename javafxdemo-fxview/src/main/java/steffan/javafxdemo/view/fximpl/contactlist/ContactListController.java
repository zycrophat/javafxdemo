package steffan.javafxdemo.view.fximpl.contactlist;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.domain.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.persistence.api.UnitOfWork;
import steffan.javafxdemo.view.api.Form;
import steffan.javafxdemo.view.api.ViewException;
import steffan.javafxdemo.view.fximpl.JavaFXSceneController;
import steffan.javafxdemo.view.fximpl.ObserveAndEditListCell;

public class ContactListController extends JavaFXSceneController<ContactList> {

    @FXML
    private ListView<Contact> contactsListView;

    @FXML
    private Button saveButton;
    private UnitOfWork unitOfWork;

    private static ObservableValue<String> contactToObservableStringValue(Contact contact) {
        return contact.idProperty().asString().
                concat(": ").
                concat(contact.firstNameProperty()).
                concat(" ").
                concat(contact.lastNameProperty());
    }

    private static String contactToEditText(Contact contact) {
        return contact.getFirstName() + ((contact.getLastName().isEmpty()) ? "" : " " + contact.getLastName());
    }

    @Override
    protected void initialize(ContactList model) {
        saveButton.disableProperty().bind(model.modifiedProperty().not());

        unitOfWork = getPersistenceContext().createUnitOfWork();
        contactsListView.setCellFactory(
                listView -> new ObserveAndEditListCell<>(
                        ContactListController::contactToObservableStringValue,
                        ContactListController::contactToEditText,
                        this::updateContact
                )
        );
        contactsListView.setEditable(true);
        contactsListView.setItems(model.getContacts());
    }

    private Contact updateContact(Contact contact, String text) {
        var names = text.split(" ", 2);
        contact.setFirstName(names[0]);

        unitOfWork.markAsModified(contact);

        if (names.length > 1) {
            contact.setLastName(names[1]);
        } else {
            contact.setLastName("");
        }
        getModel().setModified(true);
        return contact;
    }

    @FXML
    private void saveContactList() {
        try {
            getPersistenceContext().doInTransaction(ctx -> {
                unitOfWork.commit();
                getModel().setModified(false);
                unitOfWork = ctx.createUnitOfWork();
            });
        } catch (PersistenceException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler beim Speichern");
            alert.setContentText("Es ist ein Fehler beim Speichern aufgetreten");
        }
    }

    @FXML
    private void loadContactList() {
        try {
            getModel().load();
        } catch (PersistenceException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler beim Laden");
            alert.setContentText("Es ist ein Fehler beim Laden aufgetreten");
        }
    }

    @FXML
    private void createContact() {
        try {
            Form<Contact> createContactForm =
                    getFxViewManager().createCreateContactForm(new Contact());
            createContactForm.setOnSubmit(c -> {
                var contactList = getModel();
                contactList.getContacts().add(c);
                contactList.setModified(true);
                unitOfWork.markAsNew(c);

                createContactForm.hide();
            });
            createContactForm.setOnCancel(c -> createContactForm.hide());
            createContactForm.show();
        } catch (ViewException e) {
            e.printStackTrace();
        }
    }
}
