package steffan.javafxdemo.core.persistence.api;

public interface TransactionScript {

    void run(PersistenceContext ctx) throws PersistenceException;
}
