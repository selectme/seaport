package by.epam.learn.mudrahelau.model;

import by.epam.learn.mudrahelau.states.PierState;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 09.12.2019
 */

//todo this class as singleton
public class SeaPort {
    private static SeaPort seaPort;
    private Lock lock = new ReentrantLock(true);
    private int counter = 0;
    private List<Pier> piers = new ArrayList<>();


    public static SeaPort getInstance() {
        if (seaPort == null) {
            seaPort = new SeaPort();
        }
        return seaPort;
    }

    public Report shipService(Ship ship) throws InterruptedException {

        try {

            System.out.println("Ship №" + ship.getId() + " is looking for free pier...");

            for (Pier pier : piers) {
                if (pier.getPierState() == PierState.FREE) {
                    pier.getLock().lock();
                    System.out.println("Ship №" + ship.getId() + " has found a free pier №" + pier.getId() + "...");
                    pier.setPierState(PierState.OCCUPIED);
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Ship №" + ship.getId() + " has occupied the pier №" + pier.getId() + "...");

                    System.out.println("Ship " + ship.getId() + " is being process...");

                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Ship " + ship.getId() + " left the pier №" + pier.getId() + ".\n");
                    pier.getLock().unlock();
                    pier.setPierState(PierState.FREE);
                    break;
                }
            }
            return new Report(ship.getId(), counter);
        } finally {
            if (lock.tryLock()) {
                lock.unlock();
            }
        }

    }


    public void addPier(Pier pier) {
        piers.add(pier);
    }


}
