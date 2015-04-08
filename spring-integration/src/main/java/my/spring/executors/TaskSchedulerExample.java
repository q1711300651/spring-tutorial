package my.spring.executors;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;

/**
 * Реализация Spring Task Scheduler
 *
 * Методы которое имеют аннотацию @Scheduled должны возращать void и не иметь аргументов
 */
public class TaskSchedulerExample {


    /**
     * Метод будет выполняться каждые 5 секунд с определенной задержкой, где период будет расчитан с
     * момента завершения каждого предыдущего вызова.
     */
    @Scheduled(fixedDelay=5000)
    public void doSomethingWithDelay() {
        // Что ли бо что нужно выполнять с указанным периодом
    }

    /**
     * Метод будет выполняться каждые 5 секунд с определенной задержкой, где период будет расчитан с
     * момента успещного вызова метода
     */
    @Scheduled(fixedRate=5000)
    public void doSomethingWithRate() {
        // Что ли бо что нужно выполнять с указанным периодом
    }

    /**
     * initialDelay - укзаывает на время ожидание перед первым вызовом метода
     */
    @Scheduled(initialDelay=1000, fixedRate=5000)
    public void doSomethingWithInitDelayAndRate() {
        // Что ли бо что нужно выполнять с указанным периодом
    }

    /**
     * Использования крон для указание условий времени выполнения метода
     * Так же можно использовать zone аттрибут, где
     */
    @Scheduled(cron="*/5 * * * * MON-FRI")
    public void doSomethingWithCron() {
        // Что ли бо что нужно выполнять с указанным периодом
    }



}

/**
 * Trigger - JSR-236, Основная идея, что время выполнения зависит от внешиних условиях, или условиий выполнения
 *
 * public interface Trigger {
 *      Date nextExecutionTime(TriggerContext triggerContext);
 * }
 *
 * public interface TriggerContext {
 *      Date lastScheduledExecutionTime();
 *      Date lastActualExecutionTime();
 *      Date lastCompletionTime();
 * }
 *
 */
class TriggerExample {

    private TaskScheduler scheduler;

    /**
     * Эта задача будет выполняться каждые 15 минут в рабочие время, и в рабочие дни
     */
    public void doCroneTrigger() {
        scheduler.schedule( () -> System.out.println("I do something"), new CronTrigger( "* 15 9-17 * * MON-FRI" ) );
    }

}




