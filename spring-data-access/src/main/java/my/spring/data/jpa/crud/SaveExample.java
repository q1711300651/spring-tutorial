package my.spring.data.jpa.crud;

import my.spring.data.jpa.Person;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 *  Пример исопльзования наследования от SimpleJpaRepository
 *
 *  SimpleJpaRepository - позволяет получить предустановленные методы CRUD для сущностей
 */
@Repository
public class SaveExample extends SimpleJpaRepository<Person, Long > {

    public SaveExample( EntityManager em ) {
        super( Person.class, em );
    }


}
