package my.spring.environment.profiles;

import my.spring.environment.ContextParam;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

public class StartUp {


    private AnnotationConfigApplicationContext ctx;


    @Before
    public void init() {
        ctx = new AnnotationConfigApplicationContext( AppConfig.class );
    }


    /**
     * Пример указание активных профалеров, в инициализированный контекст
     * Так же можно указать в качестве параметра при запуске ява машины:
     *      -Dspring.profiles.active="profile1,profile2"
     */
    @Test
    public void testActiveProfile() {
        ctx.getEnvironment().setActiveProfiles( "dev" );
        ContextParam ctxParam = ctx.getBean( "ctxParam", ContextParam.class );
        assertEquals(ctxParam.getType(), "Dev");
    }

    /**
     * В данном случае проверяеться профалер с отрицательным занчением not dev
     */
    @Test
    public void testNotActiveProfile() {
        ContextParam ctxParam = ctx.getBean( "ctxParam", ContextParam.class );
        assertEquals( ctxParam.getType(), "Not Dev" );
    }


}
