package my.spring.environment.properties;

import my.spring.environment.ContextParam;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.Properties;

import static junit.framework.Assert.assertEquals;


public class StartUp {
    private AnnotationConfigApplicationContext ctx;


    /**
     * Реализация собственного источника для обращение к фалам настройки
     */
    private static class MyPropertySource extends PropertySource<String> {

        private Properties props = new Properties();

        private MyPropertySource() {
            super( "main property" );
            try {
                props.load( this.getClass().getResourceAsStream( "/properties/main.properties" ) );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }

        @Override
        public Object getProperty( String name ) {
            return props.getProperty( name );
        }
    }

    /**
     * Пример формирования собственных фалов настроек для контекста спринга
     */
    @Before
    public void init() {
        ctx = new AnnotationConfigApplicationContext( );
        MutablePropertySources sources = ctx.getEnvironment().getPropertySources();
        // Добавить первым в очереди, значит что в случае совподение ключа в разных фалов настроект,
        // значение будет приоритетней
        sources.addFirst( new MyPropertySource() );
        ctx.register( AppConfig.class );
        ctx.refresh();
    }


    @Test
    public void testFiledPropertyInBean() {
        ContextParam ctxParam = ctx.getBean( ContextParam.class );
        assertEquals(ctxParam.getType(), "inserted value");
    }


}
