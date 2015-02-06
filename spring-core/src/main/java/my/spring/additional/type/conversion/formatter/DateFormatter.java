package my.spring.additional.type.conversion.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Пример реализации форматтера для нового класса LocalDateTime
 */
public class DateFormatter implements Formatter<LocalDateTime> {

    // Паттер времени
    private String pattern;

    public DateFormatter( String pattern ) {
        this.pattern = pattern;
    }

    @Override
    public LocalDateTime parse( String text, Locale locale ) throws ParseException {
        try {
            return LocalDateTime.parse( text, createDateTimeFormatter( locale ) );
        } catch ( DateTimeParseException e ) {
            // В случае ошибки парсинга должно кидеться ParseException
            throw new ParseException( e.getMessage(), e.getErrorIndex() );
        } catch ( Exception e ) {
            // В противном случае IllegalArgumentException
            throw new IllegalArgumentException( e.getMessage() );
        }

    }

    @Override
    public String print( LocalDateTime dateTime, Locale locale ) {
        if ( dateTime == null ) {
            return "";
        }
        return createDateTimeFormatter( locale ).format( dateTime );
    }

    public DateTimeFormatter createDateTimeFormatter( Locale locale ) {
        return DateTimeFormatter.ofPattern( pattern, locale );
    }

}
