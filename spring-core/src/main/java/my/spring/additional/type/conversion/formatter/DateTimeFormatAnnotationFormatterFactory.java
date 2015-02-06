package my.spring.additional.type.conversion.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Реализация обработчика аннотации DateTime для связываения поля с форматером DateFormatter
 */
public class DateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateTime> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        HashSet<Class<?>> applyClasses = new HashSet<>();
        applyClasses.add( LocalDateTime.class );
        return applyClasses;
    }

    @Override
    public Printer<?> getPrinter( DateTime annotation, Class<?> fieldType ) {
        return configureFormatFrom( annotation );
    }

    @Override
    public Parser<?> getParser( DateTime annotation, Class<?> fieldType ) {
        return configureFormatFrom( annotation );
    }

    private Formatter<LocalDateTime> configureFormatFrom( DateTime annotation ) {
        return new DateFormatter( annotation.pattern() );
    }
}
