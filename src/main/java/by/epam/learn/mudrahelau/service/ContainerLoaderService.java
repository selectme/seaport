package by.epam.learn.mudrahelau.service;

import by.epam.learn.mudrahelau.model.*;
import by.epam.learn.mudrahelau.report.LoaderReport;
import by.epam.learn.mudrahelau.states.PierState;
import by.epam.learn.mudrahelau.states.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ContainerLoaderService maintenance {@link ContainerLoader}. It allows to unload containers on {@link Ship} or unload
 * containers from {@link Ship} to {@link Warehouse}.
 *
 * @author Viktar on 16.12.2019
 */
public class ContainerLoaderService {


    /**
     * {@link Warehouse}
     */
    private Warehouse warehouse;
    /**
     * Static instance.
     */
    private static final ContainerLoader CONTAINER_LOADER_INSTANCE = new ContainerLoader();

    /**
     * Time to wait after which {@link Ship} leaves the pier.
     */
    private static final int TIME_TO_WAIT = 3;
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();
    private ContainerLoader containerLoader = ContainerLoader.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ContainerLoader.class);

    /**
     * Starts maintaining {@link Ship}.
     *
     * @param ship {@link Ship}
     * @param pier {@link Pier}
     * @return {@link LoaderReport} - progress report
     */
    public LoaderReport startLoaderWork(Ship ship, Pier pier) {

        int containersProcessed = 0;

        if (ship.getShipState() == ShipState.ON_UNLOAD) {
            containersProcessed = containerLoader.getContainerLoaderService().unloadContainersFromShip(ship);
        } else if (ship.getShipState() == ShipState.ON_LOAD) {
            containersProcessed = containerLoader.getContainerLoaderService().loadContainersToShip(ship);
        }

        pier.setPierState(PierState.FREE);
        return new LoaderReport(ship, containersProcessed);
    }

    /**
     * Unloads containers from {@link Ship} and loads them into {@link Warehouse}. If there are no space in {@link Warehouse}
     * {@link Ship} will wait few seconds until free space appears. If it doesn't {@link Ship} will be forced to leave {@link Pier}.
     *
     * @param ship {@link Ship}
     * @return number of containers which were unloaded from {@link Ship}.
     */
    private int unloadContainersFromShip(Ship ship) {
        int containersProcessed = 0;
        try {
            lock.lock();
            LOGGER.info("Start unloading containers from ship #" + ship.getId());

            warehouse = Warehouse.getInstance();
            do {
                if (warehouse.hasFreeSpace() && ship.hasContainers()) {
                    Container containerToUnloadFromShip = ship.getContainersWarehouse().get(0);
                    warehouse.loadContainer(containerToUnloadFromShip);
                    ship.unloadContainer(containerToUnloadFromShip);
                    ++containersProcessed;
                } else {
                    try {
                        LOGGER.info("Waiting for free space in the warehouse");
                        if (!condition.await(TIME_TO_WAIT, TimeUnit.SECONDS)) {
                            return containersProcessed;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } while (ship.hasContainers());
            LOGGER.info("Unload from ship #" + ship.getId() + " finished");
        } finally {
            condition.signalAll();
            lock.unlock();
        }
        return containersProcessed;
    }

    /**
     * Loads containers from {@link Warehouse} and loads them into {@link Ship}. If there are no containers in {@link Warehouse}
     * {@link Ship} will wait few seconds until other {@link Ship} unloads containers. If it doesn't {@link Ship}
     * will be forced to leave {@link Pier}.
     *
     * @param ship {@link Ship}
     * @return number of containers which were loaded into {@link Ship}.
     */
    private int loadContainersToShip(Ship ship) {
        int containersProcessed = 0;
        try {
            lock.lock();
            System.out.println("Start loading containers on the ship #" + ship.getId());
            warehouse = Warehouse.getInstance();
            do {
                if (warehouse.hasContainers()) {
                    Container containerToLoadOnShip = warehouse.getContainersWarehouse().get(0);
                    ship.loadContainer(containerToLoadOnShip);
                    warehouse.unloadContainer(containerToLoadOnShip);
                    ++containersProcessed;
                } else {
                    try {
                        LOGGER.info("Waiting for containers...");
                        if (!condition.await(TIME_TO_WAIT, TimeUnit.SECONDS)) {
                            return containersProcessed;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (ship.hasFreeSpace());
            LOGGER.info("Load on the ship #" + ship.getId() + " finished");
        } finally {
            condition.signalAll();
            lock.unlock();
        }
        return containersProcessed;
    }

    static ContainerLoader getInstance() {
        return CONTAINER_LOADER_INSTANCE;
    }

    public Lock getLock() {
        return lock;
    }
}
