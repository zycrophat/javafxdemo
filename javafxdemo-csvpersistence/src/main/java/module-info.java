import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.persistence.simplepersistence.SimplePersistenceContext;

module steffan.javafxdemo.csvpersistence {
    requires steffan.javafxdemo.core;

    provides PersistenceContext with SimplePersistenceContext;
}
