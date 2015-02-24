package my.spring.transaction.declarative;

import my.spring.transaction.ExampleTransactionService;
import my.spring.transaction.SomeBean;

public class DeclarativeExampleTransactionService implements ExampleTransactionService {

    @Override
    public SomeBean getSomeBean( String name ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SomeBean getSomeBean( String name, String additional ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertSomeBean( SomeBean someBean ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateSomeBean( SomeBean someBean ) {
        throw new UnsupportedOperationException();
    }
}
