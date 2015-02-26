package my.spring.transaction.programming;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

public class TransactionTemplateExampleServiceImpl implements TransactionTemplateExampleService {

    // transactionTemplate один для всех методов класса
    private final TransactionTemplate transactionTemplate;

    public TransactionTemplateExampleServiceImpl( PlatformTransactionManager transactionManager ) {
        Assert.notNull( transactionManager, "Менеджер транзакции не должен быть null" );
        this.transactionTemplate = new TransactionTemplate( transactionManager );

        //Программноя настройка транзакции
        this.transactionTemplate.setTimeout( 30 ); // установить
        this.transactionTemplate.setIsolationLevel( TransactionDefinition.ISOLATION_DEFAULT );
    }

    @Override
    public Object someServiceMethod() {
        return transactionTemplate.execute( transactionStatus -> {
             /*
                выполенение операциив в транзакции
              */
            return "I'm over";
        });


    }
}
