package my.spring.contextevent;

import org.springframework.context.ApplicationListener;

public class MyTestEventReceiver implements ApplicationListener<MyTestEvent> {

    @Override
    public void onApplicationEvent( MyTestEvent event ) {
        System.out.println("Event received:");
        System.out.println(event.getTest());
    }
}
