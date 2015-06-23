package my.spring.webflow.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.mvc.servlet.FlowHandler;

/**
 * FlowHandler - это точка обработки потока в среде HTTP Servlets.
 * Таже можно унаследоваться от AbstractFlowHandler, где каждый метод опционален
 *
 * Что бы загрузить обработчки нужно просто обьявить его бинов в контексте, id бина должно сопвадать с
 * идентификатором потока
 *
 *  <bean name="hotels/booking" class="org.springframework.webflow.samples.booking.BookingFlowHandler" />
 *
 *
 *
 * Представленный класс выполняет следующие задачи:
 *
 *
 */
public class FlowHandlerAdapterSample  implements FlowHandler {

    /**
     * 1.Возвращает id для віполнение из контекста
     * По умолчанию выполняет поиск по части урл запроса
     * Пример:
     *      УРЛ : http://localhost/app/hotels/booking?hotelId=1
     *      ИД :  hotels/booking
     */
    public String getFlowId() {
        return null;
    }

    /**
     * 2.Создает входные параметры для вызова потока
     * По умолчанию все параметры запроса рассматриваються как входными параметрами потока ( input values )
     */
    public MutableAttributeMap<Object> createExecutionInputMap(final HttpServletRequest httpServletRequest) {
        return null;
    }

    /**
     * 3.Обрабатывает результат выполнение потока
     * По умолчанию отправляет редирект к заключительному URL потока для рестарта нового выполнения потока
     */
    public String handleExecutionOutcome(final FlowExecutionOutcome outcome, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        if (outcome.getId().equals("bookingConfirmed")) {
            return "/booking/show?bookingId=" + outcome .getOutput().get("bookingId");
        } else {
            return "/hotels/index";
        }
    }

    /**
     * 4. Обрабатывает исключение потока
     * По умолчанию любое необрабатываемое FlowException вызывает рестарт потока
     */
    public String handleException(final FlowException e, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        return null;
    }
}
