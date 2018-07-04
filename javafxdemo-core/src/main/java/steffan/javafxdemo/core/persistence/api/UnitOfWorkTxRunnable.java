package steffan.javafxdemo.core.persistence.api;

@FunctionalInterface
public interface UnitOfWorkTxRunnable {
    void run(PersistenceContext ctx, UnitOfWork unitOfWork) throws PersistenceException;
}
