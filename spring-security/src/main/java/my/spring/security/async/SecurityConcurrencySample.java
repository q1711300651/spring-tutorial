package my.spring.security.async;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Пример реализации использования многопоточного исполнения Security
 */
public class SecurityConcurrencySample {

    /**
     * Простой способ реализации многопоточного обращение к защищенным сервисам
     */
    private void invokedAsyncWithThread() {

        DelegatingSecurityContextRunnable wrppedRunnable = new DelegatingSecurityContextRunnable(() -> {
           // do some work
        });
        new Thread(wrppedRunnable).start();
    }

    /**
     * Реализация через SimpleAsyncTaskExecutor
     */
    private void invokedAsyncWithExecutor() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("user","doesnotmatter", AuthorityUtils.createAuthorityList("ROLE_USER"));
        context.setAuthentication(authentication);
        SimpleAsyncTaskExecutor delegateExecutor = new SimpleAsyncTaskExecutor();
        DelegatingSecurityContextExecutor executor =
                new DelegatingSecurityContextExecutor(delegateExecutor, context);
        Runnable originalRunnable = () -> {
            // invoke secured service
        };
        executor.execute(originalRunnable);
    }

    @Inject
    private Executor executor;

    /**
     * Реализация без участие конфигурация со стороны спринг секюрити
     */
    private void invokingWithoutSpringSecurityMentions() {
        executor.execute(() -> {
            // invoke secured service
        });
    }
}
