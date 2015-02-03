package my.spring.additional.validation;

import my.spring.additional.Address;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AddressValidator implements Validator {

    @Override
    public boolean supports( Class<?> clazz ) {
        return Address.class.equals( clazz );
    }

    @Override
    public void validate( Object target, Errors errors ) {
        ValidationUtils.rejectIfEmpty( errors, "description", "address.description.empty" );
    }
}
