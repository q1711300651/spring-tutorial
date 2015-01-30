package my.spring.method;


/**
 * Второй метод реализации иньекции создает динамически скомпелированный наследник,
 * который реализует указанный абстракный ( может быть и не абстакный метод )
 * Преимущества нет нужды в спринговской зависимости.
 */
public abstract class MethodInjectionSample_2 {

    public Command process( String commandName ) {
        Command command = createCommand();
        command.setName( commandName );
        return command;
    }

    protected abstract Command createCommand();

}
