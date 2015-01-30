package my.spring.method;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Пример реализции иньекции метода в бин c использованием ApplicationContextAware
 */
public class MethodInjectionSample_1 implements ApplicationContextAware {

    //Переменная на контейнер
    private ApplicationContext applicationContext;

    public Command process( String commandName ) {
        // получение нового обьекта из контейнера
        Command command = createCommand();
        // выполнение нужных операций
        command.setName( commandName );
        return command;
    }

    protected Command createCommand() {
        // доступ к конейнеру сприга
        return this.applicationContext.getBean( "command", Command.class );
    }

    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
