package my.spring.webflow.transition;

import my.spring.webflow.model.Booking;
import my.spring.webflow.service.BookingService;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;

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

    private static class RoomNotAvailableException extends RuntimeException {}

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
