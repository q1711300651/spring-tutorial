package my.spring.environment.profiles.annotations;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация которая вешает профалер dev, на класс или метод
 */
@Target( {ElementType.METHOD, ElementType.TYPE} )
@Retention( RetentionPolicy.RUNTIME )
@Profile( "dev" )
public @interface Developer {

}
