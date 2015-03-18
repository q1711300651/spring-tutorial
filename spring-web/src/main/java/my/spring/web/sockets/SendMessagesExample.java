package my.spring.web.sockets;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SendMessagesExample {

    /**
     * SimpMessagingTemplate используеться для отправки сообщение брокеру, по brokerChannel
     */
    @Inject
    @Qualifier("brokerMessagingTemplate") // если есть несколько бинов с этим типом, используй квалификатор
    private SimpMessagingTemplate template;

    @RequestMapping( value = "/greetings", method = POST )
    public void greet( String greeting ) {
        String text = "[" + new Date() + "]:" + greeting;
        this.template.convertAndSend( "/topic/greetings", text );
    }


}
