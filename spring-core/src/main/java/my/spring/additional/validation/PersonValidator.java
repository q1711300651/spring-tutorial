package my.spring.additional.validation;

import my.spring.additional.Person;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.inject.Inject;

/**
 * PersonValidator предостовляет валидацию для класса Person, он реализует интерфес Validator
 *
 * Можно реализовать логику для одного либо нескольких классов, но болие правильно все же для каждого
 * класса реализовывать собственный валидатор
 */
public class PersonValidator implements Validator {


    //Для реализации валидаци к вложенным обьектам используй внедрение валдиаторов
    @Inject
    private AddressValidator addressValidator;


    /**
     * Возвращает булевое значение, что указывает на возможность использовать этот валидатор для определенного класса
     */
    @Override
    public boolean supports( Class<?> clazz ) {
        return Person.class.equals( clazz );
    }



    /**
     * Собственно метод где выполняеться проверка обьектов валидируемых классов
     * И в случае, ошибок, регестрация их в классе Errors
     */
    @Override
    public void validate( Object target, Errors errors ) {
        // Стандартрый набор методов для валидации
        ValidationUtils.rejectIfEmpty( errors, "name", "name.empty" );
        Person p = ( Person ) target;

        if ( p.getAge() < 0 ) {
            // Регестрации ошибки, где указанно поле и сообщение
            errors.rejectValue( "age", "negative.value" );
        } else if ( p.getAge() > 110 ) {
            errors.rejectValue( "age", "too.darn.old" );
        }

        if ( p.getAddress() == null) {
            errors.rejectValue( "address", "address.empty" );
        } else {
            try {
                //Указать путь к полям адреса
                errors.pushNestedPath( "address" );
                ValidationUtils.invokeValidator( addressValidator, p.getAddress(), errors );
            } finally {
                errors.popNestedPath();
            }
        }
    }
}
