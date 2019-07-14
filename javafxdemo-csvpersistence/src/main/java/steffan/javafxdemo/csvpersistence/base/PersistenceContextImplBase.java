package steffan.javafxdemo.csvpersistence.base;

import steffan.javafxdemo.core.models.domainmodel.DomainObject;
import steffan.javafxdemo.core.persistence.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class PersistenceContextImplBase implements PersistenceContext {

    private Map<Class<? extends DomainObject>, Repository<? extends DomainObject>> classRepositoryMap;

    private UnitOfWork currentUnitOfWork;

    public PersistenceContextImplBase() {
        this.classRepositoryMap = new HashMap<>();
    }

    public <T extends DomainObject> void registerRepository(Class<T> domainType, Repository<T> repository) {
        this.classRepositoryMap.put(domainType, repository);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends DomainObject> Repository<T> getRepository(Class<T> clazz) throws PersistenceException {
        var tRepository = (Repository<T>) classRepositoryMap.get(clazz);
        if (tRepository == null) {
            throw new PersistenceException("Cannot find repository for class: " +  clazz.getName());
        }
        return tRepository;
    }

    @Override
    public UnitOfWork createUnitOfWork() {
        return new UnitOfWorkBaseImpl();
    }

    @Override
    public UnitOfWork getOrCreateCurrentUnitOfWork() {
        if (currentUnitOfWork == null || currentUnitOfWork.isCommitted()) {
            currentUnitOfWork = createUnitOfWork();
        }
        return currentUnitOfWork;
    }

    @Override
    public void withUnitOfWork(Consumer<UnitOfWork> unitOfWorkConsumer) {
        unitOfWorkConsumer.accept(getOrCreateCurrentUnitOfWork());
    }

    @Override
    public void withUnitOfWorkInTransaction(UnitOfWorkTxRunnable unitOfWorkTxRunnable) throws PersistenceException {
        doInTransaction(ctx -> { unitOfWorkTxRunnable.run(ctx, getOrCreateCurrentUnitOfWork()); });
    }
}
