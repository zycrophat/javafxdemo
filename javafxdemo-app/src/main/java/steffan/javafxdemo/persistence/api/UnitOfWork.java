package steffan.javafxdemo.persistence.api;

import steffan.javafxdemo.domain.DomainObject;

public interface UnitOfWork {

    <T extends DomainObject> void markAsNew(T domainObject);

    <T extends DomainObject> void markAsModified(T domainObject);

    <T extends DomainObject> void markAsDeleted(T domainObject);

    void commit() throws PersistenceException;

    boolean isCommitted();
}
