package my.spring.webflow.testing;


import org.junit.Test;
import org.springframework.binding.mapping.Mapper;
import org.springframework.binding.mapping.MappingResults;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

import my.spring.webflow.model.Booking;

/**
 * AbstractFlowExecutionTests позволяет легко протестировать поток
 */
public class TestFlowExample extends AbstractXmlFlowExecutionTests {

    /**
     * Метод загружает тест по указанному пути к xml описанию потока
     */
    @Override
    protected FlowDefinitionResource getResource(final FlowDefinitionResourceFactory resourceFactory) {
        return resourceFactory.createFileResource("src/main/webapp/WEB-INF/hotels/booking/booking.xml");
    }

    /**
     * Метод возвращает потоки, от которых может зависеть тестируемый поток
     */
    @Override
    protected FlowDefinitionResource[] getModelResources(final FlowDefinitionResourceFactory resourceFactory) {
        return new FlowDefinitionResource[]{
                resourceFactory.createFileResource("src/main/webapp/WEB-INF/common/common.xml")
        };
    }

    /**
     * Метод для внедрение зависимостей потока
     */
    @Override
    protected void configureFlowBuilderContext(final MockFlowBuilderContext builderContext) {
        Object someService = null;
        builderContext.registerBean("someService", someService);
    }

    private Booking createTestBooking() {
        return new Booking();
    }

    /**
     * Тест для проверки загрузки потока.
     * В основном проверка в правельном ли состоянии поток
     */
    @Test
    public void testStartBookingFlow() {
        MutableAttributeMap input = new LocalAttributeMap();
        input.put("hotelId", "1");
        MockExternalContext context = new MockExternalContext();
        context.setCurrentUser("keith");
        startFlow(input, context);

        assertCurrentStateEquals("enterBookingDetails");
        assertTrue(getRequiredFlowAttribute("booking") instanceof Booking);
    }

    /**
     * Тестирование обработки событий в потоке
     */
    @Test
    public void testEnterBookingDetails_Proceed() {

        //Указать текущие состояние потока
        setCurrentState("enterBookingDetails");

        getFlowScope().put("booking", createTestBooking());
        MockExternalContext context = new MockExternalContext();
        context.setEventId("proceed");
        resumeFlow(context);
        assertCurrentStateEquals("reviewBooking");
    }

    /**
     * Пример тестирования потока с под потоком
     */
    @Test
    public void testBookHotel() {
        setCurrentState("reviewHotel");

        Object hotel = new Object();
        getFlowScope().put("hotel", hotel);

        getFlowDefinitionRegistry().registerFlowDefinition(createMockBookingSubflow());

        MockExternalContext context = new MockExternalContext();
        context.setEventId("book");
        resumeFlow(context);

        // Проверить, что поток закончился на 'bookingConfirmed'
        assertFlowExecutionEnded();
        assertFlowExecutionOutcomeEquals("finish");
    }

    public Flow createMockBookingSubflow() {
        Flow mockBookingFlow = new Flow("booking");
        mockBookingFlow.setInputMapper(new Mapper() {
            public MappingResults map(Object source, Object target) {

                // Проерка входных данных
                assertEquals(1L, ((AttributeMap) source).get("hotelId"));
                return null;
            }
        });


        // Немедленно вернуть упраление родительскому потоку
        new EndState(mockBookingFlow, "bookingConfirmed");
        return mockBookingFlow;
    }


}
