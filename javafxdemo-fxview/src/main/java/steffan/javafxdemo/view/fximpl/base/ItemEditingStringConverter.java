package steffan.javafxdemo.view.fximpl.base;

import javafx.util.StringConverter;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

class ItemEditingStringConverter<T> extends StringConverter<T> {

    private Function<T, String> itemToEditText;
    private java.util.function.BiFunction<T, String, T> itemEditor;
    private Supplier<T> itemSupplier;

    public ItemEditingStringConverter(Function<T, String> itemToEditText, BiFunction<T, String, T> itemEditor, Supplier<T> itemSupplier) {
        this.itemToEditText = itemToEditText;
        this.itemEditor = itemEditor;
        this.itemSupplier = itemSupplier;
    }

    @Override
    public String toString(T object) {
        return itemToEditText.apply(itemSupplier.get());
    }

    @Override
    public T fromString(String string) {
        return itemEditor.apply(itemSupplier.get(), string);
    }
}
