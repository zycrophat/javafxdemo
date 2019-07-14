package steffan.javafxdemo.fxview.util;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class FluentNodeConfigurer {

    private List<Property<Boolean>> propertiesToBind = new LinkedList<>();
    private Function<Node, Property<Boolean>> nodeToProperty;

    private FluentNodeConfigurer(Function<Node, Property<Boolean>> nodeToProperty, Node node) {
        this.nodeToProperty = nodeToProperty;
        this.propertiesToBind.add(nodeToProperty.apply(node));
    }

    public static FluentNodeConfigurer disable(Node node) {
        return new FluentNodeConfigurer(Node::disableProperty, node);
    }

    public static FluentNodeConfigurer show(Node node) {
        return new FluentNodeConfigurer(Node::visibleProperty, node);
    }

    public FluentNodeConfigurer and(Node node) {
        this.propertiesToBind.add(nodeToProperty.apply(node));
        return this;
    }

    public void when(ObservableValue<? extends Boolean> observable) {
        propertiesToBind.forEach(p -> p.bind(observable));
    }
}
