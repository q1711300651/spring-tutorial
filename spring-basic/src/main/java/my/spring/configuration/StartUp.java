package my.spring.configuration;

import my.spring.configuration.parent.ParentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class StartUp {

    public static void main( String[] args ) {
        /**
         * Для запуска приложение не обязательно вообще форировать xml, можно получить контекст, по классу конфигурации
         *
         * new AnnotationConfigApplicationContext(AppConfig.class) - где конструктор принемает перечисление классов, для
         * контейнера
         */
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        /*
        Таже можно загружать єлементы через метод регитр, такой подход полезен, когда нужно прогрммнр настроить контекст
        приложения
        ctx.register( AppConfig.class, MyServiceImpl.class );
         */


        // Либо использовать сканирования @Configuration, мета аннотрированна Component, а значит тоже
        // подлежит сканированию
        ctx.scan( "my.spring.configuration.app" );
        // Для форирования контекста из полученных конфигураций, нужно вызвать метод refresh
        ctx.refresh();

        MyService myService = ctx.getBean( "myService", MyService.class );
        myService.execute();

        myService = ctx.getBean( "myServiceX", MyService.class );
        myService.execute();

        ParentService parentService = ctx.getBean( ParentService.class );
        parentService.work();

        ctx.destroy();
    }
}
