package my.spring.el;

import my.spring.el.add.Inventor;
import my.spring.el.add.SimpleGeneric;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Примеры реализации и работы с Spring’s Expression  Language
 *
 * ExpressionParser - отсетвсенный за парсинг выражения строки.
 *
 */
public class ExpressionParserExample {

    public static void main( String[] args ) {

        ExpressionParser parser = new SpelExpressionParser();

        //Обычный Хелло Уволд
        Expression helloWorldExpr = parser.parseExpression( "'Hello World'" );
        String helloWolrdMsg = ( String ) helloWorldExpr.getValue();
        System.out.println(helloWolrdMsg);

        // Доступ к свойству
        Expression helloWorldButesExpr = parser.parseExpression( "'Hello World'.bytes" );
        byte[] helWrdBytes = helloWorldButesExpr.getValue(byte[].class);
        System.out.println( Arrays.toString( helWrdBytes ) );

        //Вызов метода у обьекта
        Expression helWrdContExp = parser.parseExpression( "'Hello World'.concat('!')" );
        System.out.println(helWrdContExp.getValue());

        //Создание обьекта
        Expression helWrdNewExp = parser.parseExpression( "new String('hello world').toUpperCase()" );
        System.out.println(helWrdNewExp.getValue());


        /*
            Часто применяеться SpEL для предостовления преоьразовывать строку к запросу определного обьекта ( корневого )
         */
        Inventor tesla = new Inventor( "Nikola Tesla", LocalDate.of(1856,7,9), "Serbian");
        Expression nameOfInventors = parser.parseExpression( "name" );

        /*
            Использования EvaluationContext указывает что корневой обьект не будет изменен в момент выполнения,
            если корневой обьект может изменяться, на каждый запрос getValue, используй обьект на прямую
            exp.getValue(tesla);

            StandardEvaluationContext относительно затратный при создании, но из-за внутриенний оптимизации
            выполнения повторное выполнение выражений будет более быстрее, по этому рекомендуеться кешировать и
            использовать где это возможно, чем содовать новые для кождого выражения
         */
        EvaluationContext context = new StandardEvaluationContext(tesla);
        System.out.println(nameOfInventors.getValue(context));

        // Получение булевых занчений
        boolean isNikolaTesla = parser.parseExpression( "name == 'Nikola Tesla'" ).getValue( context, boolean.class );
        System.out.println("isNikolaTesla: " + isNikolaTesla);


        /*
            Конвертирования типов
            SpEL использует Спринг коре для ковертирования типов, в дополнение возможна работа и с параметрезированными
            обьектами, что повзоляет корректно работать с ними
        */
        SimpleGeneric simpleGeneric = new SimpleGeneric();
        simpleGeneric.getIntegerList().add( 0, 1 );
        parser.parseExpression( "integerList[0]" ).setValue( simpleGeneric, "2" );
        System.out.println("Is 2 -> " + simpleGeneric.getIntegerList().get( 0 ));



        /*
        Parser configuration

        Настройка парсера для SpEL выражения, настраивает поведение некоторых компонентов выражения.
        К примеру настроить так создать елемент в коллекции или массиве, если по индексу возвращаеться null, это полезно
        если выполняеться цепочка запросов
         */

        simpleGeneric = new SimpleGeneric();

        // 1.true ->  auto null reference initialization
        // 1.true ->  auto collection growing
        SpelParserConfiguration configuration = new SpelParserConfiguration( true, true );
        parser = new SpelExpressionParser( configuration );

        System.out.println( parser.parseExpression( "stringList[3]" ).getValue(simpleGeneric) );

        //После вызова getValue stringList будет хранить в себе четыри пустых строки
        System.out.println(simpleGeneric.getStringList().size());
    }
}
