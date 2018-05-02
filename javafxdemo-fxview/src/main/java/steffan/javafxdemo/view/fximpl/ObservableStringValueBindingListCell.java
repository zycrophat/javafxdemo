package steffan.javafxdemo.view.fximpl;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;

import java.util.function.Function;

public class ObservableStringValueBindingListCell<T> extends ListCell<T> {

    private Function<T, ObservableValue<String>> itemToObservableStringValue;

    ObservableStringValueBindingListCell(Function<T, ObservableValue<String>> itemToObservableStringValue) {
        this.itemToObservableStringValue = itemToObservableStringValue;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        textProperty().unbind();
        if (empty || item == null) {
            textProperty().setValue(null);
            setGraphic(null);
        } else {
            textProperty().bind(itemToObservableStringValue.apply(item));
        }
    }


}
