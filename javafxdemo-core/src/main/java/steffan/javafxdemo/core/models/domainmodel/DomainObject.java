package steffan.javafxdemo.core.models.domainmodel;

import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;

import java.util.Objects;

import static steffan.javafxdemo.core.models.util.WritableValueHelper.writableValueWithSetInPlatformThread;

public class DomainObject {

    private ReadOnlyLongWrapper id = writableValueWithSetInPlatformThread(new ReadOnlyLongWrapper());

    public DomainObject(long id) {
        this.id.setValue(id);
    }

    public long getId() {
        return id.get();
    }

    public ReadOnlyLongProperty idProperty() {
        return id.getReadOnlyProperty();
    }

    public void setId(long id) {
        this.id.setValue(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainObject that = (DomainObject) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
