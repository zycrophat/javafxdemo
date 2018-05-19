package steffan.javafxdemo.persistence.base;

import steffan.javafxdemo.domain.DomainObject;
import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.persistence.api.UnitOfWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UnitOfWorkBaseImpl implements UnitOfWork {

    private Map<Class<? extends DomainObject>, List<? extends DomainObject>> classToNewDomainObjectList;
    private Map<Class<? extends DomainObject>, List<? extends DomainObject>> classToModifiedDomainObjectList;
    private Map<Class<? extends DomainObject>, List<? extends DomainObject>> classToDeletedDomainObjectList;

    private boolean committed = false;

    public UnitOfWorkBaseImpl() {
        this.classToNewDomainObjectList = new TreeMap<>();
        this.classToModifiedDomainObjectList = new TreeMap<>();
        this.classToDeletedDomainObjectList = new TreeMap<>();
    }

    @Override
    public <T extends DomainObject> void markAsNew(T domainObject) {
        List<T> newDomainObjects = getListOfNewDomainObjectsByClass(domainObject.getClass());
        newDomainObjects.add(domainObject);
    }

    @SuppressWarnings("unchecked")
    private <T extends DomainObject> List<T>
    getListOfNewDomainObjectsByClass(Class<? extends DomainObject> domainObjectType) {
        List<? extends DomainObject> newDomainObjects =
                classToNewDomainObjectList.computeIfAbsent(domainObjectType, k -> new ArrayList<T>());
        return (List<T>) newDomainObjects;
    }

    @Override
    public <T extends DomainObject> void markAsModified(T domainObject) {
        List<T> modifiedDomainObjects = getListOfModifiedDomainObjectsByClass(domainObject.getClass());
        modifiedDomainObjects.add(domainObject);
    }

    @SuppressWarnings("unchecked")
    private <T extends DomainObject> List<T>
    getListOfModifiedDomainObjectsByClass(Class<? extends DomainObject> domainObjectType) {
        List<? extends DomainObject> modifiedDomainObjects =
                classToModifiedDomainObjectList.computeIfAbsent(domainObjectType, k -> new ArrayList<T>());
        return (List<T>) modifiedDomainObjects;
    }

    @Override
    public <T extends DomainObject> void markAsDeleted(T domainObject) {
        List<T> deletedDomainObjects = getListOfDeletedDomainObjectsByClass(domainObject.getClass());
        deletedDomainObjects.add(domainObject);
    }

    @SuppressWarnings("unchecked")
    private <T extends DomainObject> List<T>
    getListOfDeletedDomainObjectsByClass(Class<? extends DomainObject> domainObjectType) {
        List<? extends DomainObject> deletedDomainObjects =
                classToDeletedDomainObjectList.computeIfAbsent(domainObjectType, k -> new ArrayList<T>());
        return (List<T>) deletedDomainObjects;
    }

    @Override
    public void commit(PersistenceContext context) throws PersistenceException {
        context.doInTransaction(ctx -> {
            if (!this.isCommitted()) {
                insertNewDomainObjects(ctx);
                updateModifiedDomainObjects(ctx);
                deleteDeletedDomainObjects(ctx);

                setCommitted(true);
            } else {
                throw new IllegalStateException("Cannot commit (already committed)");
            }
        });
    }

    private void insertNewDomainObjects(PersistenceContext ctx) throws PersistenceException {
        for (var classListEntry : classToNewDomainObjectList.entrySet()) {
            var clazz = classListEntry.getKey();
            var repository = ctx.getRepository(clazz)
                    .orElseThrow(() -> new PersistenceException("Cannot find repository for type " + clazz.getName()));
            repository.store(getListOfNewDomainObjectsByClass(clazz));
        }
    }

    private void updateModifiedDomainObjects(PersistenceContext ctx) throws PersistenceException {
        for (var classListEntry : classToModifiedDomainObjectList.entrySet()) {
            var clazz = classListEntry.getKey();
            var repository = ctx.getRepository(clazz)
                    .orElseThrow(() -> new PersistenceException("Cannot find repository for type " + clazz.getName()));
            repository.store(getListOfModifiedDomainObjectsByClass(clazz));
        }
    }

    private void deleteDeletedDomainObjects(PersistenceContext ctx) throws PersistenceException {
        for (var classListEntry : classToDeletedDomainObjectList.entrySet()) {
            var clazz = classListEntry.getKey();
            var repository = ctx.getRepository(clazz)
                    .orElseThrow(() -> new PersistenceException("Cannot find repository for type " + clazz.getName()));
            repository.delete(getListOfNewDomainObjectsByClass(clazz));
        }
    }

    @Override
    public boolean isCommitted() {
        return committed;
    }

    private void setCommitted(boolean committed) {
        this.committed = committed;
    }
}
