package my.spring.messagesource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Locale;

public class MessageSourceExample {

    public static void main( String[] args ) {
        MessageSource resources = new ClassPathXmlApplicationContext( "messagesource/bean.xml" );
        String message = resources.getMessage( "message", null, "Default", null );
        System.out.println( message );

        Example example = ( ( ApplicationContext ) resources ).getBean( "Example", Example.class );
        example.execute(new Locale( "ru_RU" ));
        example.execute(new Locale( "en_UK" ));
    }
}
