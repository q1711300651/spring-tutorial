package my.spring.dao.orm.jdo;

import my.spring.dao.ExampleDAO;
import my.spring.dao.ExampleEntity;


import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

public class ExampleDAOImpl implements ExampleDAO {

    @Inject
    private PersistenceManagerFactory persistenceManagerFactory;

    @Override
    public void create( ExampleEntity entity ) {
        PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
    }

    @Override
    public ExampleEntity read( long id ) {
        PersistenceManager pm = null;
        try {
            pm = persistenceManagerFactory.getPersistenceManager();
            Query query = pm.newQuery( ExampleEntity.class, "id = p_id" );
            query.declareParameters( "long p_id" );
            query.setResultClass( ExampleDAOImpl.class );
            return ( ( ExampleEntity ) query.execute( id ) );
        } finally {
            if ( !( pm == null || pm.isClosed() ) ) pm.close();
        }
    }

    @Override
    public void update( ExampleEntity entity ) {

    }

    @Override
    public void delete( ExampleEntity entity ) {

    }
}
