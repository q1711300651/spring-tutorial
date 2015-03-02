package my.spring;

import java.util.Date;
import java.util.List;

public class Person {

    private String name;
    private int age;
    private Date bithDate;

    private List<PersonAttribute> attributes;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge( int age ) {
        this.age = age;
    }

    public List< PersonAttribute > getAttributes() {
        return attributes;
    }

    public void setAttributes( List< PersonAttribute > attributes ) {
        this.attributes = attributes;
    }
}
