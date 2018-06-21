package steffan.javafxdemo.app.main;

import steffan.javafxdemo.control.ApplicationControl;
import steffan.javafxdemo.control.CommandRunner;
import steffan.javafxdemo.commands.Command;
import steffan.javafxdemo.commands.CommandException;
import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.models.viewmodel.ContactList;
import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.view.api.ViewException;
import steffan.javafxdemo.view.api.ViewManager;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;


public class JavaFXAppControl implements ApplicationControl {

    private ViewManager viewManager;
    private PersistenceContext persistenceContext;

    private boolean isInitialized = false;

    private ExecutorService executorService = Executors.newSingleThreadExecutor(
            new DaemonizingThreadFactory(Executors.defaultThreadFactory())
    );

    JavaFXAppControl(ViewManager viewManager, PersistenceContext persistenceContext) {
        this.viewManager = requireNonNull(viewManager, "viewManager is null");
        this.persistenceContext = requireNonNull(persistenceContext, "persistenceContext required");
    }

    @Override
    public void initialize() {
        try {
            viewManager.initialize(this);
        } catch (ViewException e) {
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
            var contactsView = viewManager.createContactsView();

            var contacts = persistenceContext.getRepository(Contact.class).find();
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

    @Override
    public CommandRunner getCommandRunner() {
        return new CommandRunner() {
            @Override
            public <T> Future<Optional<T>> executeCommand(Command<T> command, Consumer<CommandException> onCommandException) {
                return executorService.submit(() -> {
                    try {
                        return command.run();
                    } catch (CommandException e) {
                        onCommandException.accept(e);
                        return Optional.empty();
                    }
                });
            }
        };
    }

}
