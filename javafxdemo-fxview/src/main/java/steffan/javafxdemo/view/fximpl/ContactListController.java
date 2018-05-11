package steffan.javafxdemo.view.fximpl;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.StringConverter;
import steffan.javafxdemo.app.domain.Contact;

import java.util.function.Supplier;

public class ContactListController extends JavaFXSceneController<ObservableList<Contact>> {

    @FXML
    private ListView<Contact> contactsListView;

    @Override
    protected void bindModelToElements(ObservableList<Contact> model) {
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
                                return contact;
                        }
                )
        );
        contactsListView.setEditable(true);
        contactsListView.setItems(model);
    }
}
