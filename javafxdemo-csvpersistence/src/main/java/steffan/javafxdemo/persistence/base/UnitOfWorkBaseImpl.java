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
        List<T> newDomainObjects =
                (List<T>) classToNewDomainObjectList.computeIfAbsent(domainObjectType, k -> new ArrayList<T>());
        return newDomainObjects;
    }

    @Override
    public <T extends DomainObject> void markAsModified(T domainObject) {
        List<T> modifiedDomainObjects = getListOfModifiedDomainObjectsByClass(domainObject.getClass());
        modifiedDomainObjects.add(domainObject);
    }

    @SuppressWarnings("unchecked")
    private <T extends DomainObject> List<T>
    getListOfModifiedDomainObjectsByClass(Class<? extends DomainObject> domainObjectType) {
        List<T> modifiedDomainObjects =
                (List<T>) classToModifiedDomainObjectList.computeIfAbsent(domainObjectType, k -> new ArrayList<T>());
        return modifiedDomainObjects;
    }

    @Override
    public <T extends DomainObject> void markAsDeleted(T domainObject) {
        List<T> deletedDomainObjects = getListOfDeletedDomainObjectsByClass(domainObject.getClass());
        deletedDomainObjects.add(domainObject);
    }

    @SuppressWarnings("unchecked")
    private <T extends DomainObject> List<T>
    getListOfDeletedDomainObjectsByClass(Class<? extends DomainObject> domainObjectType) {
        List<T> deletedDomainObjects =
                (List<T>) classToDeletedDomainObjectList.computeIfAbsent(domainObjectType, k -> new ArrayList<T>());
        return deletedDomainObjects;
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
            List<DomainObject> newDomainObjects = getListOfNewDomainObjectsByClass(clazz);
            for (DomainObject domainObject : newDomainObjects) {
                repository.store(domainObject, clazz);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void updateModifiedDomainObjects(PersistenceContext context) throws PersistenceException {
        for (var classListEntry : classToModifiedDomainObjectList.entrySet()) {
            var clazz = classListEntry.getKey();
            var repository = context.getRepository(clazz);
            var modifiedDomainObjects = getListOfModifiedDomainObjectsByClass(clazz);
            for(DomainObject o : modifiedDomainObjects) {
                repository.store(o, clazz);
            }
        }
    }


    private void deleteDeletedDomainObjects(PersistenceContext context) throws PersistenceException {
        for (var classListEntry : classToDeletedDomainObjectList.entrySet()) {
            var clazz = classListEntry.getKey();
            var repository = context.getRepository(clazz);
            var deletedDomainObjects = getListOfDeletedDomainObjectsByClass(clazz);
            for (DomainObject o : deletedDomainObjects) {
                repository.delete(o, clazz);
            }
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
