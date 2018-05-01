package steffan.javafxdemo.app.main;

import steffan.javafxdemo.app.api.view.View;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;


public class JavaFXDemoApp {

    private Supplier<View> viewSupplier;

    View view;

    private boolean isInitialized = false;

    public JavaFXDemoApp(Supplier<View> viewSupplier) {
        this.viewSupplier = requireNonNull(viewSupplier, "viewSupplier required");
    }

    public void initialize() {
        view = requireNonNull(viewSupplier.get(), "view is null");

        setInitialized(true);
    }

    public void start() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("App not initialized");
        }

        view.show();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }
}
