package steffan.javafxdemo.fxview.util;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

import java.util.LinkedList;
import java.util.List;

public class NodeDisablePropertyConfigurer {

    private List<Property<Boolean>> propertiesToBind = new LinkedList<>();

    private NodeDisablePropertyConfigurer(Property<Boolean> propertyToBind) {
        this.propertiesToBind.add(propertyToBind);
    }

    public static NodeDisablePropertyConfigurer disable(Node node) {
        return new NodeDisablePropertyConfigurer(node.disableProperty());
    }

    public NodeDisablePropertyConfigurer and(Node node) {
        this.propertiesToBind.add(node.disableProperty());
        return this;
    }

    public void when(ObservableValue<? extends Boolean> observable) {
        propertiesToBind.forEach(p -> p.bind(observable));
    }
}
