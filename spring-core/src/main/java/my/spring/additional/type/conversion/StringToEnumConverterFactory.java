package my.spring.additional.type.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * ConverterFactory используеться когда нужно контролировать способ конвертации для всего класса, к примеру
 * Строки в Перечисоение
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

    // Класс реализующий Converter
    private final static class StringToEnumConverter<T extends Enum> implements Converter<String, T> {

        private Class<T> enumType;

        private StringToEnumConverter( Class<T> enumType ) {
            this.enumType = enumType;
        }

        @Override
        public T convert( String source ) {
            return ( T ) Enum.valueOf( this.enumType, source.trim() );
        }
    }


    @Override
    public <T extends Enum> Converter<String, T> getConverter( Class<T> targetType ) {
        return new StringToEnumConverter<>( targetType );
    }
}
