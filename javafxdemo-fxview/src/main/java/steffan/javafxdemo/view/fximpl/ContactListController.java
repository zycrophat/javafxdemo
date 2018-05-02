package steffan.javafxdemo.view.fximpl;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import steffan.javafxdemo.app.domain.Contact;

public class ContactListController extends JavaFXSceneController<ObservableList<Contact>> {

    @FXML
    private ListView<Contact> contactsListView;

    @Override
    protected void bindModelToElements(ObservableList<Contact> model) {
        contactsListView.setCellFactory(
                listView -> new ObservableStringValueBindingListCell<>(
                        (Contact c) -> c.idProperty().asString().concat(": ").concat(c.firstNameProperty()).concat(" ").concat(c.lastNameProperty())
                )
        );
        contactsListView.setItems(model);
    }
}
