package my.spring.dao.orm.hibernate;

import my.spring.dao.ExampleDAO;
import my.spring.dao.ExampleEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExampleDAOImpl implements ExampleDAO {


    private SessionFactory sessionFactory;


    @SuppressWarnings( "unchecked" )
    public List<ExampleEntity> loadEntitiesByName(String name) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("from exampleEntity e where e.name = ?")
                .setParameter(0, name)
                .list();
    }

    @Override
    public void create( ExampleEntity entity ) {

    }

    @Override
    public ExampleEntity read( long id ) {
        return null;
    }

    @Override
    public void update( ExampleEntity entity ) {

    }

    @Override
    public void delete( ExampleEntity entity ) {

    }

    public void setSessionFactory( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
    }
}
