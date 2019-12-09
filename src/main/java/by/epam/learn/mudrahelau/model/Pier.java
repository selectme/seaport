package by.epam.learn.mudrahelau.model;

import by.epam.learn.mudrahelau.states.PierState;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 09.12.2019
 */
public class Pier {

    private Long id;
    private PierState pierState;
    private Lock lock = new ReentrantLock(true);

    public Pier(Long id, PierState pierState) {
        this.id = id;
        this.pierState = pierState;
    }

    public Long getId() {
        return id;
    }

    public PierState getPierState() {
        return pierState;
    }

    public void setPierState(PierState pierState) {
        this.pierState = pierState;
    }

    public Lock getLock() {
        return lock;
    }
}
