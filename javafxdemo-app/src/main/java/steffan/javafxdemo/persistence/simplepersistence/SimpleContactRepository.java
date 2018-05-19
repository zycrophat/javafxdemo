package steffan.javafxdemo.persistence.simplepersistence;

import steffan.javafxdemo.domain.Contact;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.persistence.api.Repository;

import java.util.Map;
import java.util.TreeMap;

public class SimpleContactRepository implements Repository<Contact> {

    private Map<Long, Contact> contactMap = new TreeMap<>();

    @Override
    public Contact find(long key) throws PersistenceException {
        return contactMap.get(key);
    }

    @Override
    public Iterable<Contact> find() throws PersistenceException {
        return contactMap.values();
    }

    @Override
    public void store(Contact object) throws PersistenceException {
        contactMap.put(object.getId(), object);
    }

    @Override
    public void store(Iterable<Contact> objects) throws PersistenceException {
        for (Contact object : objects) {
            store(object);
        }
    }

    @Override
    public void delete(Contact object) throws PersistenceException {
        contactMap.remove(object.getId());
    }

    @Override
    public void delete(Iterable<Contact> objects) throws PersistenceException {
        for (Contact object : objects) {
            delete(object);
        }
    }
}
