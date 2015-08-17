package my.spring.security.config.aop;

import org.springframework.security.access.intercept.aspectj.AspectJMethodSecurityInterceptor;

/**
 * Пример реализации проверки на права доступа при вызове методов через AspectJ java конфиг
 */
public aspect DomainObjectInstanceSecurityAspect {

    private AspectJMethodSecurityInterceptor securityInterceptor;


    pointcut domainObjectInstanceExecution(): execution(public * *(..)) && !within(DomainObjectInstanceSecurityAspect);

    Object around(): domainObjectInstanceExecution() {
        if (this.securityInterceptor == null) {
            return proceed();
        }
        return this.securityInterceptor.invoke(thisJoinPoint, () -> proceed());
    }


    public AspectJMethodSecurityInterceptor getSecurityInterceptor() {
        return securityInterceptor;
    }

    public void setSecurityInterceptor(AspectJMethodSecurityInterceptor securityInterceptor) {
        this.securityInterceptor = securityInterceptor;
    }


    public void afterPropertiesSet() throws Exception {
        if (this.securityInterceptor == null) {
            throw new IllegalArgumentException("securityInterceptor required");
        }
    }
}
