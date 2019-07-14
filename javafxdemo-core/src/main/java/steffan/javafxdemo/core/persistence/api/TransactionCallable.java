package steffan.javafxdemo.core.persistence.api;

@FunctionalInterface
public interface TransactionCallable<T> {

    T run(PersistenceContext ctx) throws PersistenceException;
}
