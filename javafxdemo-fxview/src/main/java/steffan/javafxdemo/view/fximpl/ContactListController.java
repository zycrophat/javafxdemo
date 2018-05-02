package steffan.javafxdemo.view.fximpl;

import javafx.beans.binding.StringExpression;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import steffan.javafxdemo.app.domain.Contact;
import steffan.javafxdemo.view.fximpl.JavaFXSceneController;
import steffan.javafxdemo.view.fximpl.PropertyBindingListCell;

public class ContactListController extends JavaFXSceneController<ObservableList<Contact>> {

    @FXML
    private ListView<Contact> contactsListView;

    @Override
    protected void bindModelToElements(ObservableList<Contact> model) {
        contactsListView.setCellFactory(
                listView -> new PropertyBindingListCell<>(
                        (Contact c) -> c.idProperty().asString().concat(": ").concat(c.firstNameProperty()).concat(" ").concat(c.lastNameProperty())
                )
        );
        contactsListView.setItems(model);
    }
}
