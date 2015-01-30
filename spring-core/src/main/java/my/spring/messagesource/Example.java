package my.spring.messagesource;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * Этот пример показывает как внедрять доступ к интерацилизации в бины спринга
 */
public class Example {

    private MessageSource messages;
    public void setMessages(MessageSource messages) {
        this.messages = messages;
    }


    public void execute( Locale locale ) {
        String message = this.messages.getMessage("argument.required",
                new Object [] {"userDao"}, "Required", locale);
        System.out.println(message);
    }

}
