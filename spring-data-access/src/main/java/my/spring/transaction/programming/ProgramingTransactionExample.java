package my.spring.transaction.programming;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Спринг предостовляет два способа программной работы с транзакцией
 *
 * Использвать TransactionTemplate (рекомендованно)
 * Использовать PlatformTransactionManager прямоая реализация (подобный JTA UserTransaction)
 */
public class ProgramingTransactionExample {

    public static void main( String[] args ) {

    }

    /*
        Программно инициализировать транзакцию
     */
    public void indicateRequeredRollback() {
        try {
            // логика
        } catch ( IllegalStateException e ) {
            // Программнный запуск отката
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
