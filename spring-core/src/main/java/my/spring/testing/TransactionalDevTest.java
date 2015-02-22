package my.spring.testing;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Можно также сформировать одну аннотацию для пользования во всех подходящих тестах
 */
@Target( ElementType.TYPE)
@Retention( RetentionPolicy.RUNTIME)
@ContextConfiguration({"/app-config.xml", "/test-data-access-config.xml"}) // путь к файлу дескриптеру
@ActiveProfiles("dev")
@RunWith( SpringJUnit4ClassRunner.class ) // Junit аннотация, запускать вместе
public @interface TransactionalDevTest {
}
