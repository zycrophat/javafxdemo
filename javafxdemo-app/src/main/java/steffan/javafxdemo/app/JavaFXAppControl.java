package steffan.javafxdemo.app;


import steffan.javafxdemo.core.commands.contactcommands.LoadContactsCommand;
import steffan.javafxdemo.core.control.ApplicationControl;
import steffan.javafxdemo.core.control.CommandRunner;
import steffan.javafxdemo.core.control.DaemonizingThreadFactory;
import steffan.javafxdemo.core.control.ExecutorServiceCommandRunner;
import steffan.javafxdemo.core.models.viewmodel.ContactList;
import steffan.javafxdemo.core.persistence.api.PersistenceContext;
import steffan.javafxdemo.core.view.api.UIViewException;
import steffan.javafxdemo.core.view.api.UIViewManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.requireNonNull;
import static steffan.javafxdemo.core.control.CommandRunHelper.run;


public class JavaFXAppControl implements ApplicationControl {

    private final ExecutorServiceCommandRunner executorServiceCommandRunner;
    private UIViewManager uiViewManager;
    private PersistenceContext persistenceContext;

    private boolean isInitialized = false;

    JavaFXAppControl(UIViewManager uiViewManager, PersistenceContext persistenceContext) {
        this.uiViewManager = requireNonNull(uiViewManager, "uiViewManager is null");
        this.persistenceContext = requireNonNull(persistenceContext, "persistenceContext required");
        ExecutorService executorService = Executors.newCachedThreadPool(
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

        LoadContactsCommand command = new LoadContactsCommand(this);
        run(command)
                .using(getCommandRunner())
                .onCompletion(optionalContacts -> {
                    try {
                        var contactsView = uiViewManager.createContactsUIView();
                        ContactList contactList = new ContactList(optionalContacts.orElseThrow());

                        contactsView.setModel(contactList);
                        contactsView.show();
                    } catch (UIViewException e) {
                        e.printStackTrace();
                        System.exit(666);
                    }
                })
                .execute();
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
