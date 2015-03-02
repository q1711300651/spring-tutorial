package my.spring.dispatcher;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Пример реализации диспетсчера Spring MVC, через джава код
 *
 * WebApplicationInitializer - это интерфейс  Spring MVC, который проверит пользовательскую конфигурацию
 * и автоматически добавит в контрейнер сервлетов версии 3+
 */
public class ProgramInit implements WebApplicationInitializer {

    @Override
    public void onStartup( ServletContext servletContext ) throws ServletException {

        ServletRegistration.Dynamic registration =
                servletContext.addServlet( "dispatcher", new DispatcherServlet() );
        registration.setLoadOnStartup( 1 );
        registration.addMapping( "/example/*" );
    }
}
