package my.spring.webflow.transition;

import my.spring.webflow.model.Booking;
import my.spring.webflow.service.BookingService;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.inject.Inject;

/**
 * Пример выполнение действия для перехода, подробнее смотри
 *
 * <transition on="submit" to="bookingConfirmed">
 *     <evaluate expression="bookingAction.makeBooking(booking, messageContext)" />
 * </transition>
 *
 * Действия могут вызывать ошибки для предотвращение перехода от текущего представления к следующему,
 * после ошибки выполняеться перересовка текущего, где нужно добаить соотвествующие сообщение пользователю
 *
 * Если переход использует несколько действий то при ошибки одного действия, остальне не будут выполененны
 */
public class BookingAction {

    @Inject
    private BookingService bookingService;



    public boolean makeBooking(Booking booking, MessageContext context) {
        try {
            bookingService.make(booking);
            return true;
        } catch (RoomNotAvailableException e) {
            context.addMessage(new MessageBuilder().error()
                    .defaultText("No room is available at this hotel").build());
            return false;
        }
    }
}

/**
 * Или
 * испольуя расширение классов MultiAction (для нескольких комманд ) или для одной комманды implements Action
 */
class BookingAction2 extends MultiAction {

    @Inject
    private BookingService bookingService;

    public Event makeBooking(RequestContext context) {
        try {
            Booking booking = (Booking) context.getFlowScope().get("booking");
            bookingService.make(booking);
            return success();
        } catch (RoomNotAvailableException e) {
            context.getMessageContext().addMessage(new MessageBuilder().error()
                    .defaultText("No room is available at this hotel").build());
            return error();
        }
    }
}

class RoomNotAvailableException extends RuntimeException {}