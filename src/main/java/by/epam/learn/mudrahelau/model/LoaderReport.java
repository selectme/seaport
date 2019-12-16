package by.epam.learn.mudrahelau.model;

import by.epam.learn.mudrahelau.states.ShipState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 11.12.2019
 */
public class LoaderReport {


    private Ship ship;
    private int containersProcessed;

    public LoaderReport(Ship ship, int containersProcessed) {
        this.ship = ship;
        this.containersProcessed = containersProcessed;
    }


    @Override
    public String toString() {
        String report = "";
        switch (ship.getShipState()) {
            case ON_LOAD:
                report = "Containers loaded: " + containersProcessed;
                break;
            case ON_UNLOAD:
                report = "Containers unloaded: " + containersProcessed;
                break;
        }

        return report;
    }
}
