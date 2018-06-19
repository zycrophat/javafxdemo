package steffan.javafxdemo.app.main;

import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.domain.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.persistence.simplepersistence.SimplePersistenceContext;
import steffan.javafxdemo.view.api.ViewException;
import steffan.javafxdemo.view.api.ViewManager;

import java.util.List;

import static java.util.Objects.requireNonNull;


public class JavaFXDemoApp implements DemoApplication {

    private ViewManager viewManager;
    private PersistenceContext persistenceContext;

    private boolean isInitialized = false;

    public JavaFXDemoApp(ViewManager viewManager, PersistenceContext persistenceContext) {
        this.viewManager = requireNonNull(viewManager, "viewManager is null");
        this.persistenceContext = requireNonNull(persistenceContext, "persistenceContext required");
    }

    @Override
    public void initialize() {
        setInitialized(true);
    }

    @Override
    public void start() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("App not initialized");
        }

        try {
            viewManager.initialize(this);
            var contactsView = viewManager.createContactsView();

            persistenceContext = new SimplePersistenceContext();

            var contacts = persistenceContext.getRepository(Contact.class).get().find();
            ContactList contactList = new ContactList(contacts);

            contactsView.setModel(contactList);
            contactsView.show();
        } catch (ViewException | PersistenceException e) {
            e.printStackTrace();
            System.exit(666);
        }
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    private void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    @Override
    public ViewManager getViewManager() {
        return viewManager;
    }

    @Override
    public PersistenceContext getPersistenceContext() {
        return persistenceContext;
    }

}
