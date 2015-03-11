package my.spring.webflow.events;

import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * Вызов ActionState на прямую через реализацию интрефейса
 */
public class ActionEvents implements Action {

    @Override
    public Event execute( RequestContext requestContext ) throws Exception {
        return null;
    }

    /**
     * Множественные
     */
    public static class MultipleActionEvents extends MultiAction {

        public Event actionMethod1(RequestContext context) {
            //...
            return null;
        }

        public Event actionMethod2(RequestContext context) {
            //...
            return null;
        }
        //...
    }
}
