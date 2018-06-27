package steffan.javafxdemo.app.main;

import steffan.javafxdemo.control.ApplicationControl;
import steffan.javafxdemo.control.CommandRunner;
import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.view.api.UIViewException;
import steffan.javafxdemo.view.api.UIViewManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.requireNonNull;


public class JavaFXAppControl implements ApplicationControl {

    private final ExecutorServiceCommandRunner executorServiceCommandRunner;
    private UIViewManager uiViewManager;
    private PersistenceContext persistenceContext;

    private boolean isInitialized = false;

    private ExecutorService executorService = Executors.newSingleThreadExecutor(
            new DaemonizingThreadFactory(Executors.defaultThreadFactory())
    );

    JavaFXAppControl(UIViewManager uiViewManager, PersistenceContext persistenceContext) {
        this.uiViewManager = requireNonNull(uiViewManager, "uiViewManager is null");
        this.persistenceContext = requireNonNull(persistenceContext, "persistenceContext required");
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
