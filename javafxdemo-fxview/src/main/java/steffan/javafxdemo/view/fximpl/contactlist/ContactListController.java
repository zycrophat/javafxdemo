package steffan.javafxdemo.view.fximpl.contactlist;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import steffan.javafxdemo.commands.contactcommands.ChangeContactNameCommand;
import steffan.javafxdemo.commands.contactcommands.CreateContactCommand;
import steffan.javafxdemo.commands.contactcommands.DeleteContactCommand;
import steffan.javafxdemo.commands.contactcommands.EditContactCommand;
import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.view.fximpl.base.JavaFXSceneController;
import steffan.javafxdemo.view.fximpl.base.ObserveAndEditListCell;

import static steffan.javafxdemo.control.CommandRunHelper.run;

public class ContactListController extends JavaFXSceneController<ContactList> {

    @FXML
    private ListView<Contact> contactsListView;

    @FXML
    private Button saveButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private BooleanProperty deletionIsInProgress = new SimpleBooleanProperty(false);

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
        BooleanBinding noContactIsSelectedBinding = contactsListView.getSelectionModel().selectedItemProperty().isNull();
        editButton.disableProperty().bind(noContactIsSelectedBinding);
        deleteButton.disableProperty().bind(noContactIsSelectedBinding.or(deletionIsInProgress));

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

        ChangeContactNameCommand changeContactNameCommand = new ChangeContactNameCommand(
                contact,
                firstName, lastName,
                getModel(),
                getApplicationControl().getPersistenceContext()
        );

        run(changeContactNameCommand)
                .using(getApplicationControl().getCommandRunner())
                .execute();

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
        CreateContactCommand command = new CreateContactCommand(getApplicationControl());

        run(command)
            .using(getApplicationControl().getCommandRunner())
            .onCompletion(
                optionalAddedContact ->
                    optionalAddedContact.ifPresent(addedContact -> {
                        Platform.runLater(() -> {
                            ContactList contactList = getModel();
                            contactList.addContact(addedContact);
                            contactList.setModified(true);
                        });
                    })
            )
            .execute();
    }

    @FXML
    private void editContact() {
        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
        EditContactCommand editContactCommand = new EditContactCommand(
                selectedContact,
                getModel(),
                getApplicationControl()
        );

        run(editContactCommand)
            .using(getApplicationControl().getCommandRunner())
            .execute();
    }

    @FXML
    private void deleteContact() {
        Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
        DeleteContactCommand command = new DeleteContactCommand(
                selectedContact,
                getApplicationControl());

        deletionIsInProgress.setValue(true);
        run(command)
            .using(getApplicationControl().getCommandRunner())
            .onCompletion(
                optionalRemovedContact -> optionalRemovedContact.ifPresent(removedContact -> {
                    Platform.runLater(() -> {
                        ContactList contactList = getModel();
                        contactList.removeContact(removedContact);
                        contactList.setModified(true);
                        deletionIsInProgress.setValue(false);
                    });
                })
            )
            .onCommandException(
                e -> deletionIsInProgress.setValue(false)
            )
            .execute();
    }

}
