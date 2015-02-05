package my.spring.additional.type.conversion;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartUp {
    public static void main( String[] args ) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext( "/type/conversion/beans.xml" );
        MyService myService = ctx.getBean( "myService", MyService.class );
        System.out.println( myService.doIt() );
    }
}
