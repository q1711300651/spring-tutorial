package my.spring.aspects.introductions;

import my.spring.aspects.introductions.impl.LockableImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *Механизм Introductions предостовляет возможность обьявить что обьект совета реализет указанный интерфейс
 * и предоставить реализацию этого интерфейса
 */
@Component
@Aspect
public class IntroductionsAspect {

    /*
    DeclareParents - аннотация используеться связывания одной реализации с дргих интерфейсом
     */

    @DeclareParents( value = "my.spring.aspects.introductions.Resource+", defaultImpl = LockableImpl.class)
    public static Lockable mixIn;

    @Pointcut("execution(* my.spring.aspects.introductions.Resource.setContent(..))")
    private void settingContent() {
    }


    @Around("settingContent() && this(lockable)")
    private void onSettingContent(ProceedingJoinPoint joinPoint, Lockable lockable) throws Throwable {
        System.out.println("Перед установкой контента. Locked:" + lockable.isLocked());
        if (!lockable.isLocked()) {
            joinPoint.proceed();
            System.out.println( "После установки контета:" );
        } else {
            System.out.println( "Этот рессрус заблокирован и нет возможности усиановить что-либо" );
        }
    }
}
