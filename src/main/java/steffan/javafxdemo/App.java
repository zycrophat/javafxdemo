package steffan.javafxdemo;

public class App {
    String getGreeting() {
        var msg = "Hello world!";
        return msg;
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
