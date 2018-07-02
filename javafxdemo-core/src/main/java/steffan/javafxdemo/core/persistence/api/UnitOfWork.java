package steffan.javafxdemo.core.persistence.api;

import steffan.javafxdemo.core.models.domainmodel.DomainObject;

public interface UnitOfWork {

    <T extends DomainObject> void markAsNew(T domainObject);

    <T extends DomainObject> void markAsModified(T domainObject);

    <T extends DomainObject> void markAsDeleted(T domainObject);

    void commit(PersistenceContext context) throws PersistenceException;

    boolean isCommitted();
}
