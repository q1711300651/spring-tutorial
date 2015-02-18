package my.spring.aspects.introductions.impl;

import my.spring.aspects.introductions.Lockable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class LockableImpl implements Lockable {
    private boolean lock = false;
    @Override
    public boolean isLocked() {
        return lock;
    }

    @Override
    public void lock() {
        lock = true;
    }

    @Override
    public void unlock() {
        lock = false;
    }
}
