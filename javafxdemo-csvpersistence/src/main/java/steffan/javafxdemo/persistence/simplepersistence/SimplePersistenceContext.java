package steffan.javafxdemo.persistence.simplepersistence;

import steffan.javafxdemo.models.domainmodel.Contact;
import steffan.javafxdemo.persistence.api.PersistenceException;
import steffan.javafxdemo.persistence.api.TransactionScript;
import steffan.javafxdemo.persistence.base.PersistenceContextImplBase;

public class SimplePersistenceContext extends PersistenceContextImplBase {

    {
        registerRepository(Contact.class, new SimpleContactRepository());
    }

    @Override
    public void doInTransaction(TransactionScript transactionScript) throws PersistenceException {
        transactionScript.run(this);
    }
}
