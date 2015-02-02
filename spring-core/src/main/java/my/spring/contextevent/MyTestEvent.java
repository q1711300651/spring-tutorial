package my.spring.contextevent;

import org.springframework.context.ApplicationEvent;

/**
 * Демонстрация формирования простого события приложения
 */
public class MyTestEvent extends ApplicationEvent {

    private final String test;

    public MyTestEvent( Object source, String test ) {
        super( source );
        this.test = test;
    }

    public String getTest() {
        return test;
    }
}
