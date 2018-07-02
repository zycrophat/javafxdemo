package steffan.javafxdemo.app;


import steffan.javafxdemo.core.control.ApplicationControl;
import steffan.javafxdemo.core.control.CommandRunner;
import steffan.javafxdemo.core.control.DaemonizingThreadFactory;
import steffan.javafxdemo.core.control.ExecutorServiceCommandRunner;
import steffan.javafxdemo.core.models.domainmodel.Contact;
import steffan.javafxdemo.core.models.viewmodel.ContactList;
import steffan.javafxdemo.core.persistence.api.PersistenceContext;
import steffan.javafxdemo.core.persistence.api.PersistenceException;
import steffan.javafxdemo.core.view.api.UIViewException;
import steffan.javafxdemo.core.view.api.UIViewManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.requireNonNull;


public class JavaFXAppControl implements ApplicationControl {

    private final ExecutorServiceCommandRunner executorServiceCommandRunner;
    private UIViewManager uiViewManager;
    private PersistenceContext persistenceContext;

    private boolean isInitialized = false;

    JavaFXAppControl(UIViewManager uiViewManager, PersistenceContext persistenceContext) {
        this.uiViewManager = requireNonNull(uiViewManager, "uiViewManager is null");
        this.persistenceContext = requireNonNull(persistenceContext, "persistenceContext required");
        ExecutorService executorService = Executors.newSingleThreadExecutor(
                new DaemonizingThreadFactory(Executors.defaultThreadFactory())
        );
        executorServiceCommandRunner = new ExecutorServiceCommandRunner(executorService);
    }

    @Override
    public void initialize() {
        try {
            uiViewManager.initialize(this);
        } catch (UIViewException e) {
            e.printStackTrace();
            System.exit(666);
        }
        setInitialized(true);
    }

    @Override
    public void start() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("App not initialized");
        }

        try {
            var contactsView = uiViewManager.createContactsUIView();

            var contacts = persistenceContext.getRepository(Contact.class).find();
            ContactList contactList = new ContactList(contacts);

            contactsView.setModel(contactList);
            contactsView.show();
        } catch (UIViewException | PersistenceException e) {
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
    public UIViewManager getUiViewManager() {
        return uiViewManager;
    }

    @Override
    public PersistenceContext getPersistenceContext() {
        return persistenceContext;
    }

    @Override
    public CommandRunner getCommandRunner() {
        return executorServiceCommandRunner;
    }

}
