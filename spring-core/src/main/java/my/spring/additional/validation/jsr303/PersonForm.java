package my.spring.additional.validation.jsr303;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Реализация структуры с поддержкой вадидации JavaEE JSR-303/JSR-349
 */
public class PersonForm {

    @NotNull
    @Size(max = 64)
    private String name;
    @Min( 0 )
    private int age;

    public PersonForm( String name, int age ) {
        this.name = name;
        this.age = age;
    }

    public PersonForm() {
    }

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
}
