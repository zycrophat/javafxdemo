package steffan.javafxdemo.app.main;

import steffan.javafxdemo.view.api.ViewManager;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        var app = new JavaFXDemoApp(ServiceLoader.load(ViewManager.class).findFirst()::get);
        app.initialize();
        app.start();
    }
}
