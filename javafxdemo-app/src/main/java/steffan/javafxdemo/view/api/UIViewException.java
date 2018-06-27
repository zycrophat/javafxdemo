package steffan.javafxdemo.view.api;

public class UIViewException extends Exception {
    public UIViewException() {
    }

    public UIViewException(String message) {
        super(message);
    }

    public UIViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public UIViewException(Throwable cause) {
        super(cause);
    }

    public UIViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
