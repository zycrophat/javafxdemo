package steffan.javafxdemo.app.main;

import steffan.javafxdemo.persistence.simplepersistence.SimplePersistenceContext;
import steffan.javafxdemo.view.api.ViewManager;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        var app = new JavaFXDemoApp(ServiceLoader.load(ViewManager.class).findFirst().get(), new SimplePersistenceContext());
        app.initialize();
        app.start();
    }
}
