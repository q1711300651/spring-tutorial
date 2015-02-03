package my.spring.additional.data.binding;

import my.spring.additional.Address;
import my.spring.additional.Person;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

/**
 * Пример работы с обьерткой JavaBean
 */
public class BeanWrapperSample {

    public static void main( String[] args ) {
        BeanWrapper person = new BeanWrapperImpl( new Person() );
        person.setPropertyValue( "name",  "Some Person" );

        PropertyValue propertyValue = new PropertyValue( "age", -1 );
        person.setPropertyValue( propertyValue );

        BeanWrapper address = new BeanWrapperImpl( new Address() );
        address.setPropertyValue( "description", " Some address " );

        person.setPropertyValue( "address", address.getWrappedInstance() );

        int age = ( int ) person.getPropertyValue( "age" );
        System.out.println(age);

        String description = ( String ) person.getPropertyValue( "address.description" );
        System.out.println(description);
    }
}
