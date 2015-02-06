package my.spring.el.add;

import java.time.LocalDate;

public class Inventor {
    private String name;
    private LocalDate burnDate;
    private String national;

    Inventor() {
    }

    public Inventor( String name, LocalDate burnDate, String national ) {
        this.name = name;
        this.burnDate = burnDate;
        this.national = national;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public LocalDate getBurnDate() {
        return burnDate;
    }

    public void setBurnDate( LocalDate burnDate ) {
        this.burnDate = burnDate;
    }

    public String getNational() {
        return national;
    }

    public void setNational( String national ) {
        this.national = national;
    }
}
