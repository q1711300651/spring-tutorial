package my.spring.data.jpa.query;

import my.spring.data.jpa.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Реализация именнх запросов и связывание их
 */

interface PersonRepo extends JpaRepository<Person, Long> {

    /**
     * Spring Data JPA привяжет этот метод к именовому запросу в классе Person
     */
    List<Person> findByName(String name);

    /**
     * Привязываешь напрямую к методы запроса
     * параметр nativeQuery=true -> укажет использовать SQL вместо JPQL
     *
     * @Param - используетья для именныз запросов
     */
    @Query("SELECT p FROM Person p WHERE p.name = :name")
    public List<Person> findByName2(@Param( "name" )String name);


    /**
     * entityName - внедряет имя сущности текузего репозитория в запрос.
     * Если имя не обьявленно в Entity будет использованно нозвание класса.
     * Такой подход упращает работу с параметризированными репозиториями
     */
    @Query("SELECT p FROM #{entityName} p WHERE p.name = :name")
    public List<Person> findByNameSpringEL(@Param( "name" ) String name);


    @Modifying
    @Query("update Person u set u.age = ?1 where u.name = ?2")
    int setFixedAgeOnName(int age, String name);
}


public class QuerySamples {


}
