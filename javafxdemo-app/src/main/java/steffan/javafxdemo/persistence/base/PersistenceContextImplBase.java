package steffan.javafxdemo.persistence.base;

import steffan.javafxdemo.domain.DomainObject;
import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.persistence.api.Repository;
import steffan.javafxdemo.persistence.api.UnitOfWork;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public <T extends DomainObject> Optional<Repository<T>> getRepository(Class<T> clazz) {
        return Optional.ofNullable((Repository<T>) classRepositoryMap.get(clazz));
    }

    @Override
    public UnitOfWork createUnitOfWork() {
        return new UnitOfWorkBaseImpl(this);
    }

    @Override
    public UnitOfWork getCurrentUnitOfWork() {
        return currentUnitOfWork;
    }

    @Override
    public void setCurrentUnitOfWork(UnitOfWork unitOfWork) {
        this.currentUnitOfWork = unitOfWork;
    }
}
