package my.spring;

import my.spring.method.MethodInjectionSample_1;
import my.spring.method.MethodInjectionSample_2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Пример запуска Spring в обчнычно Java - приложении
 */
public class SpringStartUp {
    public static void main( String[] args ) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext( "start-up-context.xml" );

        /*
            В данном случае Выполняеться формирования двух бинов, которые представляют два способоа реализации иньекции методов.
            Каждый класс имеет метод process который получает название бина, после обращаясь к методу, который
            представляет собой доступ к контексту и возвращает другой бин.
         */
        MethodInjectionSample_1 methodInject_1 = appContext.getBean( "methodInject_1", MethodInjectionSample_1.class );
        MethodInjectionSample_2 methodInject_2 = appContext.getBean( "methodInject_2", MethodInjectionSample_2.class );
        System.out.println(methodInject_1.process( "hello 1" ).getName());
        System.out.println(methodInject_2.process( "hello 2" ).getName());
    }
}
