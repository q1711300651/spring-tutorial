package my.spring.configuration.app;

import my.spring.configuration.MyService;
import my.spring.configuration.MyServiceImpl;
import my.spring.configuration.parent.ParentAppConfig;
import my.spring.configuration.parent.ParentService;
import my.spring.method.Command;
import my.spring.method.MethodInjectionSample_2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Пример реализации конфигурации контекста на основе Java
 *
 *     Анноатация используеться, когда нужно указать что данный класс, используеться для настройки контекста Spring,
 *     вызывает у себя аннотированые методы @Bean.
 *
 *     Ограничения:
 *      1. Конфинурационный класс не должен быть финальным
 *      2. Должен иметь конструктор без аргументов
 */
@Configuration


/*
Анноатиця указывает пакеты в параметре basePackages, которые необходимо просканировать для посика
 */
@ComponentScan(basePackages = "my.spring.configuration.components")

/*
    Аннотация @Import анологична тегу <import /> в xml, она импортирует
 */
@Import( ParentAppConfig.class )
public class AppConfig {

    // Получить доступ к возвращаемым значением из додительского конфига, можно через Autowired\Inject
    @Autowired
    private ParentService parentService;

    // или  использовать ссылку на сам конфигурационный класс

    @Autowired
    private ParentAppConfig parentAppConfig;

    /**
     * @Bean
     * Анноатация используеться, когда нужно указать что данный метод, формирует новый обьект, которй в последствии будет
     * использоваться контейнером Spring.
     *
     * Еквивалент
     *  <beans>
     *      <bean id="myService" class="my.spring.configuration.MyServiceImpl"/>
     *  </beans>
     *
     *  initMethod, destroyMethod - работают точно также как и аттрибуты  init-method and destroy-method в xml
     */

    @Bean(name = {"myService", }, initMethod = "init", destroyMethod = "cleanup")
    public MyService myService() {
        MyServiceImpl myService = new MyServiceImpl();
        /*
            Конечно можно вызвать init программно в методе, такой вызов, будет аннологичен обьявлению в аннотации
            myService.init()
         */
        return myService;

    }

    /**
     * Пример реализации иньекции для методов в Spring
     * @Bean метод может обращаться к другому @Bean только если они оба находяться в @Configuration классе
     *
     */
    @Bean
    public MethodInjectionSample_2 myMethodInjection() {

        return new MethodInjectionSample_2() {
            @Override
            protected Command createCommand() {
                return myCommand();
            }
        };
    }

    @Bean
    public Command myCommand() {
        return new Command();
    }



}
