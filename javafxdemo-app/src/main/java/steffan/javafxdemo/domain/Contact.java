package steffan.javafxdemo.domain;

import javafx.beans.property.*;

import java.util.Objects;

public class Contact extends DomainObject {

    private StringProperty firstName = new SimpleStringProperty();

    private StringProperty lastName = new SimpleStringProperty();

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

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
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
