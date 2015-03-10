package my.spring.url;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * Spring MVC предостовляет механзм для постройки и кодирования URI
 *
 * В JSP:
 *  <a href="${s:mvcUrl('PC#getPerson').arg(0,'US').buildAndExpand('123')}">Get Person</a>

 */
public class URLBuilderSample {


    public void buildURIComponent() {
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString( "http://example.com/hotels/{hotel}/bookings/{booking}" ).build();
        URI uri = uriComponents.expand( "42", "21" ).encode().toUri();


        // или

        uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("example.com").path("/hotels/{hotel}/bookings/{booking}").build()
                .expand("42", "21")
                .encode();



        //Servlets

        HttpServletRequest request = null;

        // использовать хост, схему, порт, путь и строку запроса
        // Заменит параметр "accountId"

        uriComponents = ServletUriComponentsBuilder.fromRequest(request)
                .replaceQueryParam("accountId", "{id}").build()
                .expand("123")
                .encode();
    }

}
