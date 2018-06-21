package steffan.javafxdemo.view.fximpl.contactlist;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import steffan.javafxdemo.commands.ChangeContactNameCommand;
import steffan.javafxdemo.commands.CommandException;
import steffan.javafxdemo.commands.CreateContactCommand;
import steffan.javafxdemo.commands.EditContactCommand;
import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.models.domainmodel.ContactDTO;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.view.api.Form;
import steffan.javafxdemo.view.api.ViewException;
import steffan.javafxdemo.view.fximpl.base.JavaFXSceneController;
import steffan.javafxdemo.view.fximpl.base.ObserveAndEditListCell;

import java.util.Optional;
import java.util.concurrent.*;

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
        String firstName = names[0];

        String lastName;
        if (names.length > 1) {
            lastName = names[1];

        } else {
            lastName = "";
        }

        new ChangeContactNameCommand(
                contact,
                firstName, lastName,
                getModel(),
                getApplicationControl().getPersistenceContext()
        ).run();

        return contact;
    }

    @FXML
    private void saveContactList() {
        try {
            getApplicationControl().getPersistenceContext().withUnitOfWorkInTransaction((ctx, unitOfWork) -> {
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
        getApplicationControl().getCommandRunner().executeCommand(new CreateContactCommand(getModel(), getApplicationControl()), e -> {});
    }

    @FXML
    private void editContact() {
        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
        getApplicationControl().getCommandRunner().executeCommand(
            new EditContactCommand(
                    selectedContact,
                    getModel(),
                    getApplicationControl()),
                    e -> {}
            );
    }

}
