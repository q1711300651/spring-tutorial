package my.spring.aspects.introductions;


public interface Lockable {
    boolean isLocked();
    void lock();
    void unlock();
}