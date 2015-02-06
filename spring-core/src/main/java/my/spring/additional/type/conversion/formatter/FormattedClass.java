package my.spring.additional.type.conversion.formatter;


import java.time.LocalDateTime;

/**
 * Пример реализации связывания форматтера с полей класса
 */
public class FormattedClass {

    //Связывание свойства
    @DateTime( pattern = "dd-mm-yyyy HH:mm:ss" )
    LocalDateTime localDateTime;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime( LocalDateTime localDateTime ) {
        this.localDateTime = localDateTime;
    }
}
