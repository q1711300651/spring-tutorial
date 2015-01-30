package my.spring.environment;

/**
 * Простой пример структуры, где паремтр тип будет отвечать за среду контекста
 */
public class ContextParam {

    private String type;

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }
}
