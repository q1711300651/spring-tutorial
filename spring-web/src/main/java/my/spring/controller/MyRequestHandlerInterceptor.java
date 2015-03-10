package my.spring.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

/**
 * Пример реализации собственного перехватчика запросов, для аннотированых методов @RequestHandler
 *
 * xml:
 *      <bean id="handlerMapping" class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
 *          <property name="interceptors">
 *              <bean class="my.spring.controller.MyRequestHandlerInterceptor"/>
 *          </property>
 *      </bean>
 *
 * Настройка перехатчика:
 *
 *      <bean id="officeHoursInterceptor" class="my.spring.controller.MyRequestHandlerInterceptor">
 *          <property name="openingTime" value="9"/>
 *          <property name="closingTime" value="18"/>
 *      </bean>
 *
 * Используй HandlerInterceptorAdapter, если нужен только один из методов
 */
public class MyRequestHandlerInterceptor implements HandlerInterceptor {

    private int openingTime;
    private int closingTime;


    public int getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime( int openingTime ) {
        this.openingTime = openingTime;
    }

    public int getClosingTime() {
        return closingTime;
    }

    public void setClosingTime( int closingTime ) {
        this.closingTime = closingTime;
    }

    /**
     * запускаеиться перед вызовом метожа RequestHandler. Врзвращает булевое значение,
     * где true - продолжить выполнение, false - остановть выолнение
     *
     * Если время доступа к компонентам не рабочие, то выполнить редирект на статическую страницу
     */
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if ( openingTime <= hour && hour < closingTime ) {
            return true;
        }
        response.sendRedirect( "http://host.com/outsideOfficeHours.html" );
        return false;
    }

    @Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView ) throws Exception {
    }

    @Override
    public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex ) throws Exception {

    }
}

