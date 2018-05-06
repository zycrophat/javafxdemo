package steffan.javafxdemo.app.main;

import javafx.collections.FXCollections;
import steffan.javafxdemo.app.domain.Contact;
import steffan.javafxdemo.view.api.View;
import steffan.javafxdemo.view.api.ViewException;
import steffan.javafxdemo.view.api.ViewManager;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;


public class JavaFXDemoApp {

    private Supplier<ViewManager> viewSupplier;

    private ViewManager viewManager;

    private boolean isInitialized = false;

    public JavaFXDemoApp(Supplier<ViewManager> viewSupplier) {
        this.viewSupplier = requireNonNull(viewSupplier, "viewSupplier required");
    }

    public void initialize() {
        viewManager = requireNonNull(viewSupplier.get(), "viewManager is null");

        setInitialized(true);
    }

    public void start() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("App not initialized");
        }

        try {
            viewManager.initialize();
            var contactsView = viewManager.createContactsView();
            contactsView.setModel(FXCollections.observableArrayList(
                    new Contact(1, "Andreas", "Steffan")
            ));
            contactsView.show();
        } catch (ViewException e) {
            e.printStackTrace();
            System.exit(666);
        }
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    private void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    ViewManager getViewManager() {
        return viewManager;
    }

}
