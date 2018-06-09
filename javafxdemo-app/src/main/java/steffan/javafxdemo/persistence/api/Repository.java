package steffan.javafxdemo.persistence.api;

import steffan.javafxdemo.domain.DomainObject;

public interface Repository<T extends DomainObject> {

    T find(long key) throws PersistenceException;

    Iterable<T> find() throws PersistenceException;

    void store(T object) throws PersistenceException;

    void store(Iterable<T> objects) throws PersistenceException;

    void delete(T object) throws PersistenceException;

    void delete(Iterable<T> objects) throws PersistenceException;
}
