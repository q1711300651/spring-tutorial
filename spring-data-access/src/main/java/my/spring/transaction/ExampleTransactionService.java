package my.spring.transaction;

import my.spring.transaction.SomeBean;

/**
 *
 */
public interface ExampleTransactionService {

    public SomeBean getSomeBean( String name );

    public SomeBean getSomeBean ( String name, String additional );

    void insertSomeBean( SomeBean someBean );

    void updateSomeBean( SomeBean someBean );
}
