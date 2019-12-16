package by.epam.learn.mudrahelau.service;

import by.epam.learn.mudrahelau.model.Container;
import by.epam.learn.mudrahelau.model.ContainerLoader;
import by.epam.learn.mudrahelau.model.Ship;
import by.epam.learn.mudrahelau.model.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 16.12.2019
 */
public class ContainerLoaderService {

    public static final int TIME_TO_WAIT = 3;
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();
    private Warehouse warehouse;
    private static final ContainerLoader CONTAINER_LOADER_INSTANCE = new ContainerLoader();
    private static final Logger LOGGER = LogManager.getLogger(ContainerLoader.class);


    public void unloadContainersFromShip(Ship ship) {

        try {
            lock.lock();
            LOGGER.info("Started unload containers for ship # " + ship.getId());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            warehouse = Warehouse.getInstance();
            List<Container> containersOnShip = new ArrayList<>(ship.getContainersWarehouse());
            do {
                if (warehouse.getContainersWarehouse().size() < warehouse.getWarehouseCapacity()
                        && ship.getContainersWarehouse().size() > 0) {
                    warehouse.getContainersWarehouse().add(containersOnShip.get(0));
                    ship.getContainersWarehouse().remove(0);
                } else {
                    try {
                        LOGGER.info("waiting for free space on the warehouse");
                        if (!condition.await(TIME_TO_WAIT, TimeUnit.SECONDS)) {
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } while (ship.getContainersWarehouse().size() > 0);
            LOGGER.info("unload finished");
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }


    public void loadContainersToShip(Ship ship) {

        try {
            lock.lock();
            System.out.println("Started load containers for ship # " + ship.getId());
            warehouse = Warehouse.getInstance();
            do {
                if (warehouse.hasContainers()) {
                    ship.loadContainer(warehouse.getContainersWarehouse().get(0));
                    warehouse.getContainersWarehouse().remove(0);
                } else {
                    try {
                        System.out.println("waiting for containers");
                        if (!condition.await(TIME_TO_WAIT, TimeUnit.SECONDS)) {
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (ship.getCapacity() > ship.getContainersWarehouse().size());
            System.out.println("load finished");
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }


    static ContainerLoader getInstance() {
        return CONTAINER_LOADER_INSTANCE;
    }

    public Lock getLock() {
        return lock;
    }
}
