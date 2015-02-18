package my.spring.aspects.common;


import my.spring.aspects.common.annotation.Lesson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Аспект описывает правила и реализацию АОП в Spring
 */
@Aspect
@Component
public class AspectCommons {

    /*
        Выражение Pointcut испозует AspectJ pointcut язык

        Методы Pointcut не должны иметь тела, т.к код внутри метода, выполняться не будет.
        Эти методы просто представляют собой пространсто имен, где каждое имя возрращает скомпилировнное выражения для AOP
    */
    @Pointcut("execution(* my.spring.aspects.common.target.*.walk(..))")
    private void anyWalking() {
    }

    /*
        Комбинирования и исопльзования выражение в Pointcut Может быть использована при помощи (&&,|| и !)
        Считаеться хорошей практикой комбинирования уже готовых неболших точек соединения
     */
    @Pointcut("execution(* my.spring.aspects.common.target.*.*(..)) && bean(teacher)")  // для всех бинов teacher
    private void teacherMethods() {
    }

    @Pointcut("within(my.spring.aspects.common.target.*))") // Все методы классов в пакете target
    public void inPainterAndTeacherMethods() {
    }

    @Pointcut("execution(* my.spring.aspects.common.target.*.*(..) throws Exception+)")
    public void onlyWithException() {}

    @Pointcut("teacherMethods() && args(param)")
    public void simpleMethodsWithParam(String param) {}


    /*
        Совет ( Advice )
        Логика, котороая дожна выполняться в точке соединения
        Совет бывают: до, после или вокруг точки соединения;
     */

    @Before("anyWalking()")
    public void logBeforeAnyWalking(JoinPoint joinPoint) {
        //JoinPoint это контекст вызова совета, применяеться для @Before и @After..и производных от @After
        System.out.println("Перед ходьбой:{}" + joinPoint.getTarget());
    }

    @After("anyWalking()")
    public void logAfterAnyWalking() {
        System.out.println("После ходьбы");
    }

    //После ошибки
    @AfterThrowing(value = "onlyWithException()", throwing = "e")
    public void doRecoveryActions(Exception e) {
        System.out.println("После Ошибки: " + e.getMessage());
    }

    // После с возратом
    @AfterReturning(value = "execution(String my.spring.aspects.common.target.*.*())", returning = "retVal")
    public void doAfterReturning(String retVal) {
        System.out.println("После с возращением параметра: " + retVal);
    }


    @Around("inPainterAndTeacherMethods() && !onlyWithException()")
    public void logAllMethodsPainterAndTeacher(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        /*
            ProceedingJoinPoint это контекст вызова метода ТОЛЬКО для Around совета
            Object[] args = pjp.getArgs(); // Получить аргументы вызова метода
            Object proxyObject = pjp.getThis(); // Вернет прокси обьекта
            Object object = pjp.getTarget(); // Вернет обьект в котором был вызван метод

            Если метод возращает значение то совет может вместо void обьявить Object и вернуть результат
            метода proceed
         */

        System.out.println("Вокруг: Перед выполнением: " + proceedingJoinPoint.getSignature());  //Сигнатура метода
        //proceed моежт быть вызван один раз, несколько или ни разу.
        proceedingJoinPoint.proceed();
        System.out.println( "Вокруг: После выполнением: " + proceedingJoinPoint.getSignature() );
    }

    /*
    Передача параметров
    args(type,..) - часть выражение имеет две задачи:
        1.Выделяет методы, которые имеют хоть один параметр
        2.Делает доступным аргумент в совете
 */
    @Before("teacherMethods() && args(param)")
    public void logPainterAndTeacherMethodWithStringArg(String param) {
        System.out.println("Методы учителя: Перед вызовом метода со стринговым параметром " + param);
    }


    /*
        Другой способ обьявление советов с параметрами через точку соединения

            @within,  @target, @annotation, @args - так же могут быть использованна для передачи аргументов

            argNames - используеться когда применяються несколько параметров в совет, и нужно указать вручную их
        последовательность Если перрвый параметр JoinPoint ( ProceedingJoinPoint для @Around ) можно не указывать его в
        argNames Если не указыать argNames, то Spring попробует вычеслить эту информацию из дебага,
        если указан -g:vars компилятору
     */
    @Before(value = "simpleMethodsWithParam(param)", argNames = "param")
    public void getParameter2(JoinPoint point, String param) {
        System.out.println("Методы учителя " + point.getSignature() +" : Перед вызовом метода со стринговым параметром " + param);
    }

    /*
        Пример реализации обработчика аннотации
        1. Создать аннотацию
        2. присвоеть ее методу
        3. добавить соотвествующею точку соедеение к совету
     */
    @Before( "inPainterAndTeacherMethods() && @annotation(lesson)" )
    public void checkMyAnnotation(Lesson lesson) {
        System.out.println("Методы учителя: предмет " + lesson.name());
    }

    /*
    Для работы с параметризировнными методам, можно просто указывать тип параметра в аргументе совета:
     */

    @Before( value = "execution(void my.spring.aspects.common.target.Person.doJob(..)) && args(jobType)")
    public void doGenericAdvice( String jobType ) {
        System.out.println("Перед: праметрезированный метод строкой " + jobType);
    }

    /*
       Но с колекциями такого не выйдет так как в слишком затратно проверять каждый элемент в коллекции на
       соотвествующий тип, в этом случае просто коллекция приводиться к соглашению Collection<?> и нужно в ручную
       проверить елементы
     */

    @Before("execution(void my.spring.aspects.common.target.Person.doJobs(..)) && args(jobTypes)")
    public void beforeCollectionMethod(Collection<?> jobTypes) {
        System.out.println("get collection of elements: " + jobTypes.size());
    }

    @Before("execution(void my.spring.aspects.common.target.Person.doJobs(..)) && args(jobTypes)")
    public void beforeCollectionOfStringsMethod(Collection<Integer> jobTypes) {
        // Совет все равно выполниться хотя, тип елементов коллеции и другой
        System.out.println("get collection of elements: " + jobTypes.size());
    }

}
