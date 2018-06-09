package steffan.javafxdemo.persistence.api;

import steffan.javafxdemo.domain.DomainObject;

import java.util.Optional;
import java.util.function.Consumer;

public interface PersistenceContext {

    <T extends DomainObject> Optional<Repository<T>> getRepository(Class<T> clazz);

    void doInTransaction(TransactionScript transactionScript) throws PersistenceException;

    UnitOfWork createUnitOfWork();
}
