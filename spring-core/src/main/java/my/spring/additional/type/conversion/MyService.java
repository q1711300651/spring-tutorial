package my.spring.additional.type.conversion;

import org.springframework.core.convert.ConversionService;

import javax.inject.Inject;

/**
 * Программное использования ConversionService
 */
public class MyService {

    @Inject
    private ConversionService conversionService;


    public Integer doIt() {
        return this.conversionService.convert( "155", Integer.class );
    }
}
