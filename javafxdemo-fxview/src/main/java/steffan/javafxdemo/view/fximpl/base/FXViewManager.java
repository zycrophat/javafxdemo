package steffan.javafxdemo.view.fximpl.base;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import steffan.javafxdemo.ApplicationControl;
import steffan.javafxdemo.models.domainmodel.ContactDTO;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.view.api.Form;
import steffan.javafxdemo.view.api.View;
import steffan.javafxdemo.view.api.ViewException;
import steffan.javafxdemo.view.api.ViewManager;

import java.io.IOException;
import java.net.URL;

public class FXViewManager implements ViewManager {

    private ApplicationControl applicationControl;

    private Stage primaryStage;

    @Override
    public void initialize(ApplicationControl applicationControl) throws ViewException {
        this.applicationControl = applicationControl;
        JavaFXApplication.initialize(this);
    }

    @Override
    public View<ContactList> createContactsView() throws ViewException {
        URL resource = FXViewManager.class.getResource("/steffan/javafxdemo/view/fximpl/contactlist/ContactList.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        try {
            Parent parent = loader.load();
            JavaFXSceneController<ContactList> sceneController = loader.getController();
            sceneController.configure(this, applicationControl);
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
    public Form<ContactDTO> createContactForm(ContactDTO contactDTO, String formTitle) throws ViewException {
        URL resource = FXViewManager.class.getResource("/steffan/javafxdemo/view/fximpl/contactlist/CreateContact.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        try {
            Parent parent = loader.load();
            JavaFXFormController<ContactDTO> formController = loader.getController();
            formController.configure(this, applicationControl);
            formController.setModel(contactDTO);
            Stage stage = new Stage();
            Platform.runLater( () -> {
                stage.setTitle(formTitle);
                var scene = new Scene(parent);
                stage.setScene(scene);

                stage.initOwner(primaryStage);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setOnShown(event -> {
                    double height = stage.getHeight();
                    double width = stage.getWidth();
                    stage.setMinHeight(height);
                    stage.setMaxHeight(height);
                    stage.setMinWidth(width);
                });
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

    public ApplicationControl getApplicationControl() {
        return applicationControl;
    }
}
