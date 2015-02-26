package my.spring.dao;

public class ExampleEntity {

    private long id;
    private String name;
    private double someDouble;


    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public double getSomeDouble() {
        return someDouble;
    }

    public void setSomeDouble( double someDouble ) {
        this.someDouble = someDouble;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }
}
