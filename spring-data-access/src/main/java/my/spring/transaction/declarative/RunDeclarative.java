package my.spring.transaction.declarative;

import my.spring.transaction.ExampleTransactionService;
import my.spring.transaction.SomeBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunDeclarative {

    public static void main( String[] args ) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("transaction.declarative/beans.xml");
        ExampleTransactionService fooService = (ExampleTransactionService) ctx.getBean("exampleService");
        fooService.insertSomeBean ( new SomeBean() );
    }
}
