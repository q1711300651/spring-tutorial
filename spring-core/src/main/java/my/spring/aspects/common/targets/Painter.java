package my.spring.aspects.common.targets;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope( "prototype" )
public class Painter extends Person {

    public void paint() {
        System.out.println( name + " рисует" );
    }
}
