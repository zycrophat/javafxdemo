package steffan.javafxdemo.view.fximpl;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;

import java.util.function.Function;

public class PropertyBindingListCell<T> extends ListCell<T> {

    private Function<T, ObservableValue<String>> itemToObersableStringValue;

    public PropertyBindingListCell(Function<T, ObservableValue<String>> itemToObersableStringValue) {
        this.itemToObersableStringValue = itemToObersableStringValue;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        textProperty().unbind();
        if (empty || item == null) {
            textProperty().setValue(null);
            setGraphic(null);
        } else {
            textProperty().bind(itemToObersableStringValue.apply(item));
        }
    }


}
