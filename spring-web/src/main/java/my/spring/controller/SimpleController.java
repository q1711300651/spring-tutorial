package my.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.portlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Контроллеры предостовляют доступ к приложению через веб интерфейс
 * Спринг предостовляет достаточно широкие возможности настройки и кастомизации контроллеров
 */
@Controller
public class SimpleController {

    @RequestMapping("/showAttributes")
    public void simpleRequestHandler( HttpServletRequest request ) {

        // Получить доступ к контексту через request
        WebApplicationContext webApplicationContext = ( WebApplicationContext )
                request.getAttribute( DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE );

        //Получить доступ к обработчику локализации
        LocaleResolver localeResolver = ( LocaleResolver )
                request.getAttribute( DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE );

        //Получить доступ к обработчику кастомизации
        ThemeResolver themeResolver = ( ThemeResolver ) request.getAttribute(
                DispatcherServlet.THEME_RESOLVER_ATTRIBUTE );

        AbstractHandlerExceptionResolver exceptionResolver = webApplicationContext.
                getBean( DispatcherServlet.HANDLER_EXCEPTION_RESOLVER_BEAN_NAME,
                        AbstractHandlerExceptionResolver.class );


        // И тк. далие через DispatcherServlet.*

    }


}
