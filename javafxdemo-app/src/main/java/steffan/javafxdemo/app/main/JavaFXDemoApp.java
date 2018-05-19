package steffan.javafxdemo.app.main;

import javafx.collections.FXCollections;
import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.domain.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.persistence.simplepersistence.SimplePersistenceContext;
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

            PersistenceContext persistenceContext = new SimplePersistenceContext();
            var repository = persistenceContext.getRepository(Contact.class).get();
            repository.store(new Contact(1L, "Andreas", "Steffan"));
            ContactList contactList = new ContactList(persistenceContext);
            contactList.load();
            contactsView.setModel(contactList);
            contactsView.show();
        } catch (ViewException | PersistenceException e) {
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
