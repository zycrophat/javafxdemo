package steffan.javafxdemo.app.main;

import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.view.api.ViewManager;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        var app = new JavaFXAppControl(ServiceLoader.load(ViewManager.class).findFirst().get(), ServiceLoader.load(PersistenceContext.class).findFirst().get());
        app.initialize();
        app.start();
    }
}
