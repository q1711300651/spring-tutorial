package my.spring;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PersonService {

    public List<Person> findByName(String name);
    public Person findById(long id);
    public Map<String, Person> getPersonsByName();
    public Map<Date, Person> getPersonsByBithDay();
    public void addNewPerson(Person person);
}
