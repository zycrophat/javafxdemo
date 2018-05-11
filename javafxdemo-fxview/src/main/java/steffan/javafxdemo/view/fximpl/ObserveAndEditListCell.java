package steffan.javafxdemo.view.fximpl;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.cell.TextFieldListCell;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ObserveAndEditListCell<T> extends TextFieldListCell<T> {

    private Function<T, ObservableValue<String>> itemToObservableStringValue;

    public ObserveAndEditListCell(Function<T, ObservableValue<String>> itemToObservableStringValue, Function<T, String> itemToEditText, BiFunction<T, String, T> itemEditor) {
        this.itemToObservableStringValue = itemToObservableStringValue;

        this.setConverter(new ItemEditingStringConverter<T>(itemToEditText, itemEditor, this::getItem));
    }

    @Override
    public void startEdit() {
        textProperty().unbind();
        super.startEdit();
    }

    @Override
    public void commitEdit(T value) {
        super.commitEdit(value);
        textProperty().bind(itemToObservableStringValue.apply(value));
    }

    @Override
    public void updateItem(T item, boolean empty) {
        textProperty().unbind();
        super.updateItem(item, empty);

        if (empty || item == null) {
            textProperty().setValue(null);
            setGraphic(null);
        } else {
            textProperty().bind(itemToObservableStringValue.apply(item));
        }
    }

}
