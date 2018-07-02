package steffan.javafxdemo.core.persistence.api;

import steffan.javafxdemo.core.models.domainmodel.DomainObject;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends DomainObject> {

    Optional<T> find(long key) throws PersistenceException;

    List<T> find() throws PersistenceException;

    void store(T object) throws PersistenceException;

    void delete(T object) throws PersistenceException;
}
