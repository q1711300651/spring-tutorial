package my.spring.remouting.jaxws;

import my.spring.remouting.Account;
import my.spring.remouting.AccountService;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;


/**
 * JAX-WS реализует AccountService, простым делегированием
 *
 * Это класс-оболочка является необходимым, поскольку JAX-WS требует работы со специальной системой
 * EndPoint классов.
 * Если существующие сервисы должны быть экспортированы в оболочку, то она должна наследоваться от
 * SpringBeanAutowiringSupport для простой совместимости со Spring
 *
 * Это класс зарегистрирован на стороне сервера реализацей JAX-WS.
 * В случае Java EE 5, можно просто определить как сервлет в web.xml,
 * с сервером определения, что это конечная точка JAX-WS
 * Имя сервлета обычно должен соответствовать указанным именем WS службы.
 *
 * Веб-сервис управляет жизненным циклом экземпляров этого класса.
 *
 *
 *
 * В конце, просто описать контекст в web.xml:
 *
 *  <servlet>
 *      <servlet-name>AccountService</servlet-name>
 *      <servlet-class>my.spring.remouting.jaxws.AccountServiceEndPoint
 *      </servlet-class>
 *  </servlet>
 *  <servlet-mapping>
 *      <servlet-name>AccountService</servlet-name>
 *      <url-pattern>/AccountService</url-pattern>
 *  </servlet-mapping>
 *
 */
@WebService(serviceName = "AccountService")
public class AccountServiceEndPoint extends SpringBeanAutowiringSupport {

    @Inject
    private AccountService accountService;

    @WebMethod
    public void insertAccount( Account account ) {
        accountService.insertAccount( account );
    }

    @WebMethod
    public List< Account > getAccounts() {
        return accountService.getAccounts();
    }
}
