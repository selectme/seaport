package by.epam.learn.mudrahelau.service;

import by.epam.learn.mudrahelau.model.*;
import by.epam.learn.mudrahelau.states.PierState;
import by.epam.learn.mudrahelau.states.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 16.12.2019
 */
public class ContainerLoaderService {

    private static final int TIME_TO_WAIT = 3;
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();
    private Warehouse warehouse;
    private static final ContainerLoader CONTAINER_LOADER_INSTANCE = new ContainerLoader();
    private static final Logger LOGGER = LogManager.getLogger(ContainerLoader.class);
    private ContainerLoader containerLoader = ContainerLoader.getInstance();


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
