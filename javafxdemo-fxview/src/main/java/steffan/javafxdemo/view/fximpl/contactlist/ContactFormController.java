package steffan.javafxdemo.view.fximpl.contactlist;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.view.fximpl.base.JavaFXFormController;

public class ContactFormController extends JavaFXFormController<Contact> {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @Override
    protected void initialize(Contact model) {
        firstNameTextField.textProperty().bindBidirectional(model.firstNameProperty());
        lastNameTextField.textProperty().bindBidirectional(model.lastNameProperty());
    }

    @FXML
    private void submit() {
        unbindTextFields();
        getOnSubmit().accept(getModel());
    }

    private void unbindTextFields() {
        firstNameTextField.textProperty().unbind();
        firstNameTextField.textProperty().unbind();
    }

    @FXML
    private void cancel() {
        unbindTextFields();
        getOnCancel().accept(getModel());
    }
}
