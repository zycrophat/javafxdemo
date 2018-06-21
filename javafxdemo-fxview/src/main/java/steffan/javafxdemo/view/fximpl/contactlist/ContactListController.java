package steffan.javafxdemo.view.fximpl.contactlist;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.models.domainmodel.ContactDTO;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.view.api.Form;
import steffan.javafxdemo.view.api.ViewException;
import steffan.javafxdemo.view.fximpl.base.JavaFXSceneController;
import steffan.javafxdemo.view.fximpl.base.ObserveAndEditListCell;

public class ContactListController extends JavaFXSceneController<ContactList> {

    @FXML
    private ListView<Contact> contactsListView;

    @FXML
    private Button saveButton;

    @FXML
    private Button editButton;

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
        editButton.disableProperty().bind(contactsListView.getSelectionModel().selectedItemProperty().isNull());

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

        getApplicationControl().getPersistenceContext().withUnitOfWork(unitOfWork -> {
            unitOfWork.markAsModified(contact);
        });

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
            getApplicationControl().getPersistenceContext().withUnitOfWorkInTransaction( (ctx, unitOfWork) -> {
                unitOfWork.commit(ctx);
                getModel().setModified(false);
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
            getApplicationControl().getPersistenceContext().doInTransaction(ctx -> {
                var contacts = ctx.getRepository(Contact.class).find();

                getModel().getContacts().setAll(contacts);
                getModel().setModified(false);
            });
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
            Form<ContactDTO> createContactForm =
                    getFxViewManager().createContactForm(new ContactDTO(), "Create Contact");
            createContactForm.setOnSubmit(c -> {
                var contactList = getModel();

                Contact contact = new Contact(c.getId(), c.getFirstName(), c.getLastName());
                contactList.addContact(contact);
                contactList.setModified(true);
                getApplicationControl().getPersistenceContext().withUnitOfWork( unitOfWork -> {
                    unitOfWork.markAsNew(contact);
                });

                createContactForm.hide();
            });
            createContactForm.setOnCancel(c -> createContactForm.hide());
            createContactForm.show();
        } catch (ViewException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editContact() {
        try {
            Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
            ContactDTO contactDTO = new ContactDTO();
            contactDTO.setFirstName(selectedContact.getFirstName());
            contactDTO.setLastName(selectedContact.getLastName());
            Form<ContactDTO> createContactForm =
                    getFxViewManager().createContactForm(contactDTO, "Edit Contact");

            createContactForm.setOnSubmit(c -> {

                selectedContact.setFirstName(c.getFirstName());
                selectedContact.setLastName(c.getLastName());

                getModel().setModified(true);
                getApplicationControl().getPersistenceContext().withUnitOfWork(unitOfWork -> {
                    unitOfWork.markAsModified(selectedContact);
                });

                createContactForm.hide();
            });

            createContactForm.setOnCancel(c -> createContactForm.hide());

            createContactForm.show();
        } catch (ViewException e) {
            e.printStackTrace();
        }
    }
}
