package by.epam.learn.mudrahelau.model;

import by.epam.learn.mudrahelau.states.PierState;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Model object that represents a pier.
 *
 * @author Viktar on 09.12.2019
 */
public class Pier {
    /**
     * Pier's identification number.
     */
    private Long id;
    /**
     * {@link PierState}
     */
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
