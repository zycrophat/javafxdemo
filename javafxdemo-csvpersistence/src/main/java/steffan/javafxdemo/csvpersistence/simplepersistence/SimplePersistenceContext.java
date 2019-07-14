package steffan.javafxdemo.csvpersistence.simplepersistence;

import steffan.javafxdemo.core.models.domainmodel.Contact;
import steffan.javafxdemo.core.persistence.api.PersistenceException;
import steffan.javafxdemo.core.persistence.api.TransactionCallable;
import steffan.javafxdemo.core.persistence.api.TransactionScript;
import steffan.javafxdemo.csvpersistence.base.PersistenceContextImplBase;

public class SimplePersistenceContext extends PersistenceContextImplBase {

    {
        registerRepository(Contact.class, new SimpleContactRepository());
    }

    @Override
    public <T> T doInTransaction(TransactionCallable<T> transactionCallable) throws PersistenceException {
        return transactionCallable.run(this);
    }

    @Override
    public void doInTransaction(TransactionScript transactionScript) throws PersistenceException {
        transactionScript.run(this);
    }
}
