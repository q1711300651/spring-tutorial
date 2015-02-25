package my.spring.transaction.programming;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
