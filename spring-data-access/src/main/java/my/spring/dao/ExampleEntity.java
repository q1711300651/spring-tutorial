package my.spring.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ExampleEntity {

    @Id
    private long id;
    @Column
    private String name;
    @Column
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
