package my.spring.aspects;

import my.spring.aspects.common.target.Painter;
import my.spring.aspects.common.target.Teacher;
import my.spring.aspects.introductions.Lockable;
import my.spring.aspects.introductions.Resource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class StartUp {

    public static void main( String[] args ) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext( EnableAspect.class );
        Teacher teacher = ctx.getBean(Teacher.class);
        teacher.setName("Ross");

        Teacher teacher2 = ctx.getBean(Teacher.class);
        teacher2.setName("Jhon");

        Painter painter = ctx.getBean(Painter.class);
        painter.setName("Pedro");


        teacher.walk();
        teacher.teach();

        painter.walk();
        painter.paint();
        try { painter.getMistake(); } catch ( Exception e ) { /*..*/ }

        teacher2.getName();
        teacher2.doJob( "new job" ); // запустит совет
        teacher2.doJob( 55 ); // не запустит совет
        teacher2.doJobs(  Arrays.asList("Hello", ",", "World", "Kitty") );

        System.out.println("***********************************************************************************");
        Resource resource = ctx.getBean(Resource.class);
        Lockable lockable = (Lockable) resource;
        lockable.lock();
        resource.setContent( "new content" );

    }
}
