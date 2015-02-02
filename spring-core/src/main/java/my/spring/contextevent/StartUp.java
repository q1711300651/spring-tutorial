package my.spring.contextevent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartUp {

    public static void main( String[] args ) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext( "contextevent/bean.xml" );
        MyTestEventService testEventService = ctx.getBean( "testEventService", MyTestEventService.class );
        testEventService.executeService();
    }
}
