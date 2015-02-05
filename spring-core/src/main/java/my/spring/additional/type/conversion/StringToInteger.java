package my.spring.additional.type.conversion;

import org.springframework.core.convert.converter.Converter;

/**
 * Пример простого конвертера строки в число
 */
public class StringToInteger implements Converter<String, Integer> {

    @Override
    public Integer convert( String source ) {
        System.out.println("run!");
        return Integer.valueOf( source );
    }
}
