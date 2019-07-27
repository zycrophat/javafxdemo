package steffan.javafxdemo.core.models.domainmodel;

import javafx.beans.property.Property;

import java.util.function.Function;

public class ProxyProvider {
    public static Function<Property<?>, Property<?>> propertyProxyTransformer = Function.identity();

    @SuppressWarnings("unchecked")
    public static <T extends Property<?>> T proxy(T property) {
        return (T) propertyProxyTransformer.apply(property);
    }
}