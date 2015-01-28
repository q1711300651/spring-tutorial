package my.spring.configuration;


public class MyServiceImpl implements MyService {

    private void init() {
        System.out.println("init my service");
    }

    private void cleanup() {
        System.out.println("delete my service");
    }


    @Override
    public void execute() {
        System.out.println("My Service work");
    }
}
