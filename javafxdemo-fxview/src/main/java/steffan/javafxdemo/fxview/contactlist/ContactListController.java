package steffan.javafxdemo.fxview.contactlist;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import steffan.javafxdemo.core.commands.contactcommands.*;
import steffan.javafxdemo.core.models.domainmodel.Contact;
import steffan.javafxdemo.core.models.viewmodel.ContactList;
import steffan.javafxdemo.fxview.base.JavaFXSceneController;
import steffan.javafxdemo.fxview.base.ObserveAndEditListCell;
import steffan.javafxdemo.fxview.util.PlatformHelper;

import static steffan.javafxdemo.core.control.CommandRunHelper.run;
import static steffan.javafxdemo.fxview.util.FluentNodeConfigurer.disable;
import static steffan.javafxdemo.fxview.util.FluentNodeConfigurer.show;


public class ContactListController extends JavaFXSceneController<ContactList> {

    @FXML
    private ListView<Contact> contactsListView;

    @FXML
    private Button saveButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ProgressIndicator progressIndicator;

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
        configureButtons(model);

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

    private void configureButtons(ContactList model) {
        disable(saveButton).when(modelIsNotModified().or(deletionIsInProgress));
        disable(editButton).and(deleteButton).when(noContactIsSelected().or(deletionIsInProgress));
        show(progressIndicator).when(deletionIsInProgress);
    }

    private BooleanBinding noContactIsSelected() {
        return contactsListView.getSelectionModel().selectedItemProperty().isNull();
    }

    private BooleanBinding modelIsNotModified() {
        return getModel().modifiedProperty().not();
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
        CommitUnitOfWorkCommand command = new CommitUnitOfWorkCommand(getApplicationControl());
        run(command)
                .using(PlatformHelper.getPlatformCommandRunner())
                .onCompletion(o -> getModel().setModified(false))
                .onCommandException(e -> {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler beim Speichern");
                    alert.setContentText("Es ist ein Fehler beim Speichern aufgetreten");
                })
                .execute();
    }

    @FXML
    private void loadContactList() {
        LoadContactsCommand command = new LoadContactsCommand(getApplicationControl());
        run(command)
                .using(getApplicationControl().getCommandRunner())
                .onCompletion(optionalContacts ->
                        Platform.runLater(() -> {
                            getModel().getContacts().setAll(optionalContacts.orElseThrow());
                            getModel().setModified(false);
                        })
                )
                .onCommandException(e -> {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Fehler beim Laden");
                        alert.setContentText("Es ist ein Fehler beim Laden aufgetreten");
                    });
                })
                .execute();
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
                getApplicationControl()
        );

        run(editContactCommand)
            .using(getApplicationControl().getCommandRunner())
                .onCompletion(optionallyEditedContact -> {
                    if (optionallyEditedContact.isPresent()) {
                        Platform.runLater(() -> getModel().setModified(true));
                    }
                })
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
