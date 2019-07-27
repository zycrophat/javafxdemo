package steffan.javafxdemo.core.models.domainmodel;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.Objects;

import static steffan.javafxdemo.core.models.domainmodel.ProxyProvider.proxy;

public class Contact extends DomainObject {

    private ReadOnlyStringWrapper firstName = proxy(new ReadOnlyStringWrapper());

    private ReadOnlyStringWrapper lastName = proxy(new ReadOnlyStringWrapper());

    public Contact() {
        super(-1);
    }

    public Contact(String firstName, String lastName) {
        super(-1);
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
    }

    public Contact(Long id, String firstName, String lastName) {
        super(id);
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public ReadOnlyStringProperty firstNameProperty() {
        return firstName.getReadOnlyProperty();
    }

    public void setFirstName(String firstName) {
        this.firstName.setValue(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public ReadOnlyStringProperty lastNameProperty() {
        return lastName.getReadOnlyProperty();
    }

    public void setLastName(String lastName) {
        this.lastName.setValue(lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        if (!super.equals(o)) return false;
        Contact contact = (Contact) o;
        return Objects.equals(getFirstName(), contact.getFirstName()) &&
                Objects.equals(getLastName(), contact.getLastName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getFirstName(), getLastName());
    }
}
