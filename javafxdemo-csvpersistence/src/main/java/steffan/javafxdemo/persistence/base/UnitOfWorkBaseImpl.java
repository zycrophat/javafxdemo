package steffan.javafxdemo.persistence.base;

import steffan.javafxdemo.models.domainmodel.DomainObject;
import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.persistence.api.UnitOfWork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitOfWorkBaseImpl implements UnitOfWork {

    private Map<Class<? extends DomainObject>, List<? extends DomainObject>> classToNewDomainObjectList;
    private Map<Class<? extends DomainObject>, List<? extends DomainObject>> classToModifiedDomainObjectList;
    private Map<Class<? extends DomainObject>, List<? extends DomainObject>> classToDeletedDomainObjectList;

    private boolean committed = false;

    public UnitOfWorkBaseImpl() {
        this.classToNewDomainObjectList = new HashMap<>();
        this.classToModifiedDomainObjectList = new HashMap<>();
        this.classToDeletedDomainObjectList = new HashMap<>();
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
        if (!this.isCommitted()) {
            insertNewDomainObjects(context);
            updateModifiedDomainObjects(context);
            deleteDeletedDomainObjects(context);

            setCommitted();
        } else {
            throw new IllegalStateException("Cannot commit (already committed)");
        }
    }

    @SuppressWarnings("unchecked")
    private void insertNewDomainObjects(PersistenceContext context) throws PersistenceException {
        for (var classListEntry : classToNewDomainObjectList.entrySet()) {
            var clazz = classListEntry.getKey();
            var repository =  context.getRepository(clazz);
            repository.store(getListOfNewDomainObjectsByClass(clazz));
        }
    }

    @SuppressWarnings("unchecked")
    private void updateModifiedDomainObjects(PersistenceContext context) throws PersistenceException {
        for (var classListEntry : classToModifiedDomainObjectList.entrySet()) {
            var clazz = classListEntry.getKey();
            var repository = context.getRepository(clazz);
            repository.store(getListOfModifiedDomainObjectsByClass(clazz));
        }
    }


    private void deleteDeletedDomainObjects(PersistenceContext context) throws PersistenceException {
        for (var classListEntry : classToDeletedDomainObjectList.entrySet()) {
            var clazz = classListEntry.getKey();
            var repository = context.getRepository(clazz);
            repository.delete(getListOfDeletedDomainObjectsByClass(clazz));
        }
    }

    @Override
    public boolean isCommitted() {
        return committed;
    }

    private void setCommitted() {
        this.committed = true;
    }
}
