package my.spring.dao;

public interface ExampleDAO {

    public void create(ExampleEntity entity);
    public ExampleEntity read(long id);
    public void update(ExampleEntity entity);
    public void delete(ExampleEntity entity);
}
