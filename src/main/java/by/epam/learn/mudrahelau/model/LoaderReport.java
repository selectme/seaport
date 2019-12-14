package by.epam.learn.mudrahelau.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 11.12.2019
 */
public class LoaderReport {

    private long shipId;
    private int counter;



    public LoaderReport(long shipId) {
        this.shipId = shipId;
    }


    @Override
    public String toString() {
        return "Ship # " + shipId + " left the warehouse";
    }
}
