package my.spring.additional.validation.jsr303;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Пример добовление класса валидации для сервиса
 */
public class MyService {

    @Inject
    private Validator validator;

    // Пример выполнения проверки в сервесе
    public void checkPerson( PersonForm person ) {
        Set<ConstraintViolation<PersonForm>> validate = validator.validate( person );
        for ( ConstraintViolation<PersonForm> result : validate ) {
            System.out.print( result.getMessage() );
            System.out.print( " for " );
            System.out.println(result.getInvalidValue());
            System.out.println("#################################");
        }
        if ( validate.isEmpty() ) {
            System.out.println("It's Ok!");
            System.out.println("#################################");
        }
    }
}
