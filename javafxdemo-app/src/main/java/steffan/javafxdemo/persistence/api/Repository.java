package steffan.javafxdemo.persistence.api;

import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.domain.DomainObject;

import java.util.List;

public interface Repository<T extends DomainObject> {

    T find(long key) throws PersistenceException;

    List<T> find() throws PersistenceException;

    void store(T object) throws PersistenceException;

    void store(List<Contact> objects) throws PersistenceException;

    void delete(T object) throws PersistenceException;

    void delete(List<Contact> objects) throws PersistenceException;
}
