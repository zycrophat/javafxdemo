import steffan.javafxdemo.core.persistence.api.PersistenceContext;
import steffan.javafxdemo.csvpersistence.simplepersistence.SimplePersistenceContext;

module steffan.javafxdemo.csvpersistence {
    requires steffan.javafxdemo.core;

    provides PersistenceContext with SimplePersistenceContext;
}
