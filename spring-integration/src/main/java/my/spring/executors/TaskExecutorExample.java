package my.spring.executors;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.core.task.TaskExecutor;

import javax.inject.Inject;
import java.lang.reflect.Method;

/**
 *
 *
 * Настройка TaskExecutor
 *  <bean id="taskExecutor" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
 *      <property name="corePoolSize" value="5" />
 *      <property name="maxPoolSize" value="10" />
 *      <property name="queueCapacity" value="25" />
 *  </bean>
 *
 * Подробнее о разновидности TaskExecutor:
 * notes/core/executors/TaskExecutor types.txt
 */
public class TaskExecutorExample {

    @Inject
    private TaskExecutor taskExecutor;

    private class MessagePrinterTask implements Runnable {
        private String message;
        public MessagePrinterTask(String message) {
            this.message = message;
        }
        public void run() {
            System.out.println(message);
        }
    }

    public void printMassages() {
        for( int i = 0; i < 25; i++ ) {
            taskExecutor.execute( new MessagePrinterTask("Message" + i));
        }
    }

}


/**
 * Когда асинхронные методы, возвращают Future то передачу исключение в случие его возникновения,
 * выполняються через метод get
 *
 * Но, когда метод возвращает void, нужно использовать AsyncUncaughtExceptionHandler
 *
 * По умолчанию исключение просто выводяться в лог, для регистрации нужно опрделеить в
 * @AsyncConfigurer java либо в  task:annotation-driven XML
 */
class MyAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException( Throwable throwable, Method method, Object... objects ) {
            // Обработать исключение
    }
}