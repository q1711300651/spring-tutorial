package my.spring.el;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Примеры реализации и работы с Spring’s Expression  Language
 *
 * ExpressionParser - отсетвсенный за парсинг выражения строки.
 *
 */
public class ExpressionParserExample {

    public static void main( String[] args ) throws NoSuchMethodException {

        ExpressionParser parser = new SpelExpressionParser();

        //Обычный Хелло Уволд
        Expression helloWorldExpr = parser.parseExpression( "T(java.io.File).separator" );
        String helloWolrdMsg = ( String ) helloWorldExpr.getValue();
        System.out.println(helloWolrdMsg);

//        // Доступ к свойству
//        Expression helloWorldButesExpr = parser.parseExpression( "'Hello World'.bytes" );
//        byte[] helWrdBytes = helloWorldButesExpr.getValue(byte[].class);
//        System.out.println( Arrays.toString( helWrdBytes ) );
//
//        //Вызов метода у обьекта
//        Expression helWrdContExp = parser.parseExpression( "'Hello World'.concat('!')" );
//        System.out.println(helWrdContExp.getValue());
//
//        //Создание обьекта
//        Expression helWrdNewExp = parser.parseExpression( "new String('hello world').toUpperCase()" );
//        System.out.println(helWrdNewExp.getValue());
//
//
//        /*
//            Часто применяеться SpEL для предостовления преоьразовывать строку к запросу определного обьекта ( корневого )
//         */
//        Inventor tesla = new Inventor( "Nikola Tesla", LocalDate.of(1856,7,9), "Serbian");
//        Expression nameOfInventors = parser.parseExpression( "name" );
//
//        /*
//            Использования EvaluationContext указывает что корневой обьект не будет изменен в момент выполнения,
//            если корневой обьект может изменяться, на каждый запрос getValue, используй обьект на прямую
//            exp.getValue(tesla);
//
//            StandardEvaluationContext относительно затратный при создании, но из-за внутриенний оптимизации
//            выполнения повторное выполнение выражений будет более быстрее, по этому рекомендуеться кешировать и
//            использовать где это возможно, чем содовать новые для кождого выражения
//         */
//        StandardEvaluationContext context = new StandardEvaluationContext(tesla);
//        System.out.println(nameOfInventors.getValue(context));
//
//        // Получение булевых занчений
//        boolean isNikolaTesla = parser.parseExpression( "name == 'Nikola Tesla'" ).getValue( context, boolean.class );
//        System.out.println("isNikolaTesla: " + isNikolaTesla);
//
//
//        /*
//            Конвертирования типов
//            SpEL использует Спринг коре для ковертирования типов, в дополнение возможна работа и с параметрезированными
//            обьектами, что повзоляет корректно работать с ними
//        */
//        SimpleGeneric simpleGeneric = new SimpleGeneric();
//        simpleGeneric.getIntegerList().add( 0, 1 );
//        parser.parseExpression( "integerList[0]" ).setValue( simpleGeneric, "2" );
//        System.out.println("Is 2 -> " + simpleGeneric.getIntegerList().get( 0 ));
//
//
//
//        /*
//        Parser configuration
//
//        Настройка парсера для SpEL выражения, настраивает поведение некоторых компонентов выражения.
//        К примеру настроить так создать елемент в коллекции или массиве, если по индексу возвращаеться null, это полезно
//        если выполняеться цепочка запросов
//         */
//
//        simpleGeneric = new SimpleGeneric();
//
//        // 1.true ->  auto null reference initialization
//        // 1.true ->  auto collection growing
//        SpelParserConfiguration configuration = new SpelParserConfiguration( true, true );
//        parser = new SpelExpressionParser( configuration );
//
//        System.out.println( parser.parseExpression( "stringList[3]" ).getValue(simpleGeneric) );
//
//        //После вызова getValue stringList будет хранить в себе четыри пустых строки
//        System.out.println(simpleGeneric.getStringList().size());
//
//        // переменные
//
//        context.setVariable( "newName", "Mike Tesla" );
//        parser.parseExpression( "name = #newName" ).getValue( context );
//        System.out.println( tesla.getName() );
//
//        /*
//        #this и #root
//            #this - ссылаеться на обьект в контексте которого выполнеться вырожение
//            #root - всегда ссылается на корневой обьект
//         */
//        List<Integer> primes = new ArrayList<>();
//        primes.addAll(Arrays.asList(2,3,5,7,11,13,17));
//        context.setVariable( "primes", primes );
//
//        @SuppressWarnings( "unchecked" )
//        List<Integer> moreThenTenPrimes = ( List<Integer> ) parser.parseExpression( "#primes.?[#this>10]" )
//                .getValue( context );
//        System.out.println( moreThenTenPrimes );
//
//        /*
//            Функции
//            Регистрация функции
//        */
//        context.registerFunction( "reverseString", SimpleUtils.class.getDeclaredMethod( "reverseString", String.class ) );
//        String reverseStringResult = parser.parseExpression( "#reverseString('hello')" ).getValue( context, String.class );
//        System.out.println(reverseStringResult);
//
//
//        /*
//            Внутрениее Шаблоны
//            Позволяет сочетать статические и динамисеские элементы в выражении:
//         */
//        ParserContext templateContext = new ParserContext() {
//
//            @Override
//            public boolean isTemplate() {
//                return true;
//            }
//
//            @Override
//            public String getExpressionPrefix() {
//                return "#{";
//            }
//
//            @Override
//            public String getExpressionSuffix() {
//                return "}";
//            }
//        };
//
//        String templateResult = parser.parseExpression( "Случайное значение: #{T(java.lang.Math).random()}",
//                templateContext ).getValue( String.class );
//        System.out.println(templateResult);
    }
}
