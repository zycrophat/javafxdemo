package steffan.javafxdemo.persistence.api;

public interface UnitOfWorkTxRunnable {
    void run(PersistenceContext ctx, UnitOfWork unitOfWork) throws PersistenceException;
}
