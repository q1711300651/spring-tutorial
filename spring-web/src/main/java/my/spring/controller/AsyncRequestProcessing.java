package my.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Callable;

/**
 * Со Spring MVC 3.2 реализованна поддежка механизма асинхоной обработки запросов Servlet 3
 * Позволяет выполнять тяжолые операции в отдельном пуле воркеров
 *  Несколько особенностей:
 * 1.Сервлет может законить свою обработку, но ответ будет поддерживаться другим потоком
 * 2.Метод request.startAsync() возвращает AsyncContext, которые предостовляет методы для
 *   ассинхоной работы. К примеру метод dispatch в AsyncContext, выполяеться как и обчный
 *   dispatch только в другом потоке
 *
 * Что бы использовать  Async Request Processing нжно обновить web.xml до версии 3+
 * Диспетчер сервлетов и Фильтры должны иметь опицию:
 *              <async-supported>true</async-supported>
 * Пример настройки web.xml:
 *
 *
 *  <web-app xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
 *  version="3.0">
 *       <filter>
 *           <filter-class>org.springframework.~.OpenEntityManagerInViewFilter</filter-class>
 *           <async-supported>true</async-supported>
 *       </filter>
 *       <filter-mapping>
 *           <filter-name>Spring OpenEntityManagerInViewFilter</filter-name>
 *           <url-pattern>/*</url-pattern>
 *           <dispatcher>REQUEST</dispatcher>
 *           <dispatcher>ASYNC</dispatcher>
 *       </filter-mapping>
 *       </web-app>
 *
 */
public class AsyncRequestProcessing {


    /**
     * Spring MVC вызвает Callable в отдельном потоке с помошью TaskExecutor и тогда вернет Callable
     * 1. Контроллер вернет  Callable
     * 2. Spring MVC начнет async обработку и выполнение Callable в TaskExecutor для выполенение в отдельном потоке
     * 3. DispatcherServlet и все Filter’s закончат работу, но ответ остаеться открытым
     * 4. Callable вернет результат и Spring MVC диспечерит вызовет обратно Servlet контейнер
     * 5.DispatcherServlet опять вызываетья и выпоолнение возвращаеться вместе с ассинхонными результатам из Callable
     */
    @RequestMapping(method= RequestMethod.POST)
    public Callable<String> processUpload(final MultipartFile file ) {
       return () -> {
           // some separate work;
           return "some file";
       };
    }

    /**
     * Если метод возвращает обьект DeferredResult. В данном случае возращает значение из отедльного потока
     * но такой поток не известен Spring MVC, к примеру с помошью JMS
     */
    @RequestMapping("/quotes")
    @ResponseBody
    public DeferredResult<String> quotes() {
        DeferredResult<String> deferredResult = new DeferredResult<String>();
        // Save the deferredResult in in-memory queue ...
        return deferredResult;
    }

    //
    // deferredResult.setResult(data);

}
