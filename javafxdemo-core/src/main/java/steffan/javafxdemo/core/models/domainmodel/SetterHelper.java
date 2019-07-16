package steffan.javafxdemo.core.models.domainmodel;

import javafx.beans.value.WritableValue;

import static steffan.javafxdemo.core.control.PlatformHelper.runLaterOrOnPlatformThreadAndWait;

class SetterHelper {

    static <T> void set(WritableValue<T> writableValue, T newValue) {
        runLaterOrOnPlatformThreadAndWait(() -> setNewValue(writableValue, newValue));
    }

    private static <T> void setNewValue(WritableValue<T> writableValue, T newValue) {
        writableValue.setValue(newValue);
    }

}
