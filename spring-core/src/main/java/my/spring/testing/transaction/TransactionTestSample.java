package my.spring.testing.transaction;

import my.spring.testing.TransactionalDevTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TransactionConfiguration(transactionManager="txMgr", defaultRollback=false)
@TransactionalDevTest
public class TransactionTestSample {

    @BeforeTransaction
    public void verifyInitialDatabaseState() {
        // инициализируеться до транзакции
    }
    @Before
    public void setUpTestDataWithinTransaction() {
        // запускаеться перед тестом но вместе с транзакицей
    }

    @Test
    @Rollback(true) // переопределяет для метода опции отката
    public void modifyDatabaseWithinTransaction() {
        // логика что изменяет состояние бд
    }

    @After
    public void tearDownWithinTransaction() {
        // выполнеяеться после теста но вместе с транзакцией
    }


    @AfterTransaction
    public void verifyFinalDatabaseState() {
        // выполняеться после откакта транзакции
    }
}
