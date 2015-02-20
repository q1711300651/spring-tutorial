package my.spring.aspects.common.targets;

import my.spring.aspects.common.annotation.Lesson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope( "prototype" )
public class Teacher extends Person {

    @Lesson(name = "Математика")
    public void teach() {
        System.out.println( name + " учит" );
    }

}
