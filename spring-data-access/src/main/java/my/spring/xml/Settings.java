package my.spring.xml;

/**
 * Пример маршалинга, демаршалинга с помошью спринг простой модели настроек
 */

public class Settings {

    private boolean somethingEnabled;

    public boolean isSomethingEnabled() {
        return somethingEnabled;
    }

    public void setSomethingEnabled( boolean somethingEnabled ) {
        this.somethingEnabled = somethingEnabled;
    }

}
