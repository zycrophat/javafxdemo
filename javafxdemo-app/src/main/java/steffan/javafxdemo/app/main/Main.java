package steffan.javafxdemo.app.main;

import steffan.javafxdemo.app.api.view.View;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        JavaFXDemoApp app = new JavaFXDemoApp(ServiceLoader.load(View.class).findFirst()::get);
        app.initialize();
        app.start();
    }
}
