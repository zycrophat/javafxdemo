package steffan.javafxdemo.app;

import steffan.javafxdemo.core.persistence.api.PersistenceContext;
import steffan.javafxdemo.core.view.api.UIViewManager;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        var app = new JavaFXAppControl(
                ServiceLoader.load(UIViewManager.class).findFirst().orElseThrow(() -> new RuntimeException("Cannot load UIViewManager")),
                ServiceLoader.load(PersistenceContext.class).findFirst().orElseThrow(() -> new RuntimeException("Cannot load PersistenceContext"))
        );
        app.initialize();
        app.start();
    }
}
