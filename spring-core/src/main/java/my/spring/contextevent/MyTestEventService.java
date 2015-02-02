package my.spring.contextevent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * Для публикации собственных событий, нужно вызвать метод  publishEvent() в ApplicationEventPublisher.
 * Обычно создаеться класс, что реализует ApplicationEventPublisherAware, и регестрируеться как бин
 */
public class MyTestEventService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher( ApplicationEventPublisher applicationEventPublisher ) {
        this.publisher = applicationEventPublisher;
    }

    public void executeService() {
        //do some work...
        // and publish event
        publisher.publishEvent( new MyTestEvent( this, "test") );
    }
}
