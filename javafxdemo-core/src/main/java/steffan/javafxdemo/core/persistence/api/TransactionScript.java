package steffan.javafxdemo.core.persistence.api;

@FunctionalInterface
public interface TransactionScript {

    void run(PersistenceContext ctx) throws PersistenceException;
}
