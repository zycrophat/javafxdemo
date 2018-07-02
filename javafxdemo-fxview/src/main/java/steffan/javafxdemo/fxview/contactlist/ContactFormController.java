package steffan.javafxdemo.fxview.contactlist;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import steffan.javafxdemo.core.models.domainmodel.ContactDTO;
import steffan.javafxdemo.fxview.base.JavaFXFormController;

public class ContactFormController extends JavaFXFormController<ContactDTO> {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @Override
    protected void initialize(ContactDTO model) {
        firstNameTextField.setText(model.getFirstName());
        lastNameTextField.setText(model.getLastName());
    }

    @FXML
    private void submit() {
        var contactDTO = getModel();
        contactDTO.setFirstName(firstNameTextField.getText());
        contactDTO.setLastName(lastNameTextField.getText());

        getOnSubmit().accept(contactDTO);
    }

    @FXML
    private void cancel() {
        getOnCancel().accept(getModel());
    }
}
