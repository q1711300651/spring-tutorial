package my.spring.aspects.common.targets;

import java.util.Collection;

public class Person {

    protected String name;

    public void walk() {
        System.out.println( name + " идет" );
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void getMistake() throws Exception {
        throw new RuntimeException( name + " mistake" );
    }

    public<T> void doJob( T jobType ) {
        System.out.println(name + " делает " + jobType);
    }

    public<T> void doJobs( Collection<T> jobsTypes ) {

    }
}
