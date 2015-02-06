package my.spring.additional.validation.jsr303;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

public class StartUp {

    public static void main( String[] args ) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext( "/validating/jsr303/beans.xml" );
        MyService myService = ctx.getBean( "myService", MyService.class );
        PersonForm person = new PersonForm();
        myService.checkPerson( person );
        person.setName( "Name" );
        myService.checkPerson( person );
        person.setAge( -1 );
        myService.checkPerson( person );
        person.setAge( 1 );
        myService.checkPerson( person );


        // Конфигурация с DataBinder(BeanWrapper)

        DataBinder binder = new DataBinder( person );
        binder.addValidators( ctx.getBean( "validator", Validator.class ) );

        binder.validate();
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.addPropertyValue( "age", -1 );
        mutablePropertyValues.addPropertyValue( "name", "Hello" );
        binder.bind( mutablePropertyValues );
        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();
        bindingResult.getAllErrors().forEach( er -> System.out.println(er.getCode()) );
    }
}
