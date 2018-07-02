package steffan.javafxdemo.app;

import steffan.javafxdemo.core.persistence.api.PersistenceContext;
import steffan.javafxdemo.core.view.api.UIViewManager;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        var app = new JavaFXAppControl(
                ServiceLoader.load(UIViewManager.class).findFirst().get(),
                ServiceLoader.load(PersistenceContext.class).findFirst().get()
        );
        app.initialize();
        app.start();
    }
}
