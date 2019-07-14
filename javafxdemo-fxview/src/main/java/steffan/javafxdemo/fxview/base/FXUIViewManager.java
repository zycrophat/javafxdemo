package steffan.javafxdemo.fxview.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import steffan.javafxdemo.core.control.ApplicationControl;
import steffan.javafxdemo.core.models.domainmodel.ContactDTO;
import steffan.javafxdemo.core.models.viewmodel.ContactList;
import steffan.javafxdemo.core.view.api.UIForm;
import steffan.javafxdemo.core.view.api.UIView;
import steffan.javafxdemo.core.view.api.UIViewException;
import steffan.javafxdemo.core.view.api.UIViewManager;
import steffan.javafxdemo.fxview.util.PlatformHelper;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class FXUIViewManager implements UIViewManager {

    private static final String SCENE_BASE_PATH = "/steffan/javafxdemo/fxview";
    private ApplicationControl applicationControl;

    private Stage primaryStage;

    @Override
    public void initialize(ApplicationControl applicationControl) {
        this.applicationControl = applicationControl;
        JavaFXApplication.initialize(this);
    }

    @Override
    public UIView<ContactList> createContactsUIView() throws UIViewException {
        URL resource = FXUIViewManager.class.getResource(SCENE_BASE_PATH + "/contactlist/ContactList.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        try {
            Parent parent = loader.load();
            JavaFXSceneController<ContactList> sceneController = loader.getController();
            sceneController.configure(this, applicationControl);

            configureStage(() -> primaryStage, stage -> {
                stage.setScene(new Scene(parent));
                stage.setTitle("Contact list");
            });

            return new FXUIView<>(primaryStage, sceneController);
        } catch (IOException e) {
            throw new UIViewException(e);
        }
    }

    @Override
    public UIForm<ContactDTO> createContactUIForm(ContactDTO contactDTO, String formTitle) throws UIViewException {
        URL resource = FXUIViewManager.class.getResource(SCENE_BASE_PATH + "/contactlist/CreateContact.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        try {
            Parent parent = loader.load();
            JavaFXFormController<ContactDTO> formController = loader.getController();
            formController.configure(this, applicationControl);
            formController.setModel(contactDTO);

            Stage configuredStage = createAndConfigureStage(stage -> {
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

            return new FXUIForm<>(configuredStage, formController);
        } catch (IOException e) {
            throw new UIViewException(e);
        }
    }

    private Stage createAndConfigureStage(Consumer<Stage> stageConfigurator) throws UIViewException {
        return configureStage(Stage::new, stageConfigurator);
    }

    private Stage configureStage(Supplier<Stage> stageSupplier, Consumer<Stage> stageConfigurator) throws UIViewException {
        return PlatformHelper.callLater(() -> {
            Stage stage = stageSupplier.get();
            stageConfigurator.accept(stage);
            return stage;
        });
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
