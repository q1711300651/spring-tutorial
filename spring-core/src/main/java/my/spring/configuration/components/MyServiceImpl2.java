package my.spring.configuration.components;

import my.spring.configuration.MyService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service("myServiceX")
public class MyServiceImpl2 implements MyService {

    /*
        Аннотации жизненного цикла обьекта
        После создания
        Перед уничтожением
     */
    @PostConstruct
    private void init() {
        System.out.println("init my service 2");
    }


    @PreDestroy
    private void cleanup() {
        System.out.println("delete my service 2");
    }

    @Override
    public void execute() {
        System.out.println("Service 2 work...");
    }
}
