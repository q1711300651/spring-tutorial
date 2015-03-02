package my.spring.dao.orm.jpa;

import my.spring.dao.ExampleDAO;
import my.spring.dao.ExampleEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ExampleDAOImpl implements ExampleDAO {


    /**
     * Так как EntityManager поток не безопасный обьект напрямую его внедрить не следует,
     * но используя аннотацию PersistenceContext можно получить защищенный прокси EntityManager
     *
     * PersistenceContext имеент настройку, где указываеться спобой получение менеджера
     *  1.PersistenceContextType.TRANSACTION - потоко безопасный
     *  2.PersistenceContextType.EXTENDED - кторый не потокобезопасный и может испольховать вместе с
     *  элементами что храняться в сессии
     *
     */
    @PersistenceContext
    private EntityManager manager;


    @Override
    public void create( ExampleEntity entity ) {
        manager.persist( entity );
    }

    @Override
    public ExampleEntity read( long id ) {
        return manager.find( ExampleEntity.class, id );
    }

    @Override
    public void update( ExampleEntity entity ) {
        manager.merge( entity );
    }

    @Override
    public void delete( ExampleEntity entity ) {
        manager.remove( manager.merge( entity ) );
    }
}
