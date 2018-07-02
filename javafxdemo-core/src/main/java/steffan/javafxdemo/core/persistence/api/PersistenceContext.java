package steffan.javafxdemo.core.persistence.api;

import steffan.javafxdemo.core.models.domainmodel.DomainObject;

import java.util.function.Consumer;

public interface PersistenceContext {

    <T extends DomainObject> Repository<T> getRepository(Class<T> clazz) throws PersistenceException;

    void doInTransaction(TransactionScript transactionScript) throws PersistenceException;

    UnitOfWork getOrCreateCurrentUnitOfWork();

    UnitOfWork createUnitOfWork();

    void withUnitOfWork(Consumer<UnitOfWork> unitOfWorkConsumer);

    void withUnitOfWorkInTransaction(UnitOfWorkTxRunnable unitOfWorkTxRunnable) throws PersistenceException;
}
