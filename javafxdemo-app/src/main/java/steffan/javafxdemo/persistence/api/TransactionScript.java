package steffan.javafxdemo.persistence.api;

public interface TransactionScript {

    void run(PersistenceContext ctx) throws PersistenceException;
}
