package steffan.javafxdemo.view.fximpl.base;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import steffan.javafxdemo.app.main.DemoApplication;
import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.domain.ContactList;
import steffan.javafxdemo.view.api.Form;
import steffan.javafxdemo.view.api.View;
import steffan.javafxdemo.view.api.ViewException;
import steffan.javafxdemo.view.api.ViewManager;

import java.io.IOException;
import java.net.URL;

public class FXViewManager implements ViewManager {

    private DemoApplication demoApplication;

    private Stage primaryStage;

    @Override
    public void initialize(DemoApplication demoApplication) throws ViewException {
        this.demoApplication = demoApplication;
        JavaFXApplication.initialize(this);
    }

    @Override
    public View<ContactList> createContactsView() throws ViewException {
        URL resource = FXViewManager.class.getResource("/steffan/javafxdemo/view/fximpl/contactlist/ContactList.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        try {
            Parent parent = loader.load();
            JavaFXSceneController<ContactList> sceneController = loader.getController();
            sceneController.configure(this, demoApplication.getPersistenceContext());
            Platform.runLater( () -> {
                primaryStage.setScene(new Scene(parent));
                primaryStage.setTitle("Contact list");
            });

            return new FXView<>(primaryStage, sceneController);
        } catch (IOException e) {
            throw new ViewException(e);
        }
    }

    @Override
    public Form<Contact> createCreateContactForm(Contact contact) throws ViewException {
        URL resource = FXViewManager.class.getResource("/steffan/javafxdemo/view/fximpl/contactlist/CreateContact.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        try {
            Parent parent = loader.load();
            JavaFXFormController<Contact> formController = loader.getController();
            formController.configure(this, demoApplication.getPersistenceContext());
            formController.setModel(contact);
            Stage stage = new Stage();
            Platform.runLater( () -> {
                stage.setTitle("Create Contact");
                stage.setScene(new Scene(parent));

                stage.initOwner(primaryStage);
                stage.initModality(Modality.WINDOW_MODAL);
            });

            return new FXForm<>(stage, formController);
        } catch (IOException e) {
            throw new ViewException(e);
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public DemoApplication getDemoApplication() {
        return demoApplication;
    }
}
