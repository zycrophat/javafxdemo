package steffan.javafxdemo.persistence.api;

import steffan.javafxdemo.domain.DomainObject;

import java.util.Optional;

public interface PersistenceContext {

    <T extends DomainObject> Repository<T> getRepository(Class<T> clazz) throws PersistenceException;

    void doInTransaction(TransactionScript transactionScript) throws PersistenceException;

    UnitOfWork getCurrentUnitOfWork();

    void setCurrentUnitOfWork(UnitOfWork unitOfWork);

    UnitOfWork createUnitOfWork();
}
