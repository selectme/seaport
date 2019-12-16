package by.epam.learn.mudrahelau.model;


import by.epam.learn.mudrahelau.service.ContainerLoaderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 11.12.2019
 */
public class ContainerLoader {

    private ContainerLoaderService containerLoaderService = new ContainerLoaderService();

    //    public static final int TIME_TO_WAIT = 3;
//    private Lock lock = new ReentrantLock(true);
//    private Condition condition = lock.newCondition();
//    private Warehouse warehouse;
    private static final ContainerLoader CONTAINER_LOADER_INSTANCE = new ContainerLoader();

    //    private static final Logger LOGGER = LogManager.getLogger(ContainerLoader.class);
//
//
//    void unloadContainersFromShip(Ship ship) {
//
//        try {
//            lock.lock();
//            LOGGER.info("Started unload containers for ship # " + ship.getId());
//
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            warehouse = Warehouse.getInstance();
//            List<Container> containersOnShip = new ArrayList<>(ship.getContainersWarehouse());
//            do {
//                if (warehouse.getContainersWarehouse().size() < warehouse.getWarehouseCapacity()
//                        && ship.getContainersWarehouse().size() > 0) {
//                    warehouse.getContainersWarehouse().add(containersOnShip.get(0));
//                    ship.getContainersWarehouse().remove(0);
//                } else {
//                    try {
//                        LOGGER.info("waiting for free space on the warehouse");
//                        if (!condition.await(TIME_TO_WAIT, TimeUnit.SECONDS)) {
//                            return;
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } while (ship.getContainersWarehouse().size() > 0);
//            LOGGER.info("unload finished");
//        } finally {
//            condition.signalAll();
//            lock.unlock();
//        }
//    }
//
//
//    void loadContainersToShip(Ship ship) {
//
//        try {
//            lock.lock();
//            System.out.println("Started load containers for ship # " + ship.getId());
//            warehouse = Warehouse.getInstance();
//            do {
//                if (warehouse.hasContainers()) {
//                    ship.loadContainer(warehouse.getContainersWarehouse().get(0));
//                    warehouse.getContainersWarehouse().remove(0);
//                } else {
//                    try {
//                        System.out.println("waiting for containers");
//                        if (!condition.await(TIME_TO_WAIT, TimeUnit.SECONDS)) {
//                            return;
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            while (ship.getCapacity() > ship.getContainersWarehouse().size());
//            System.out.println("load finished");
//        } finally {
//            condition.signalAll();
//            lock.unlock();
//        }
//    }
//
//
   public static ContainerLoader getInstance() {
        return CONTAINER_LOADER_INSTANCE;
    }

    public ContainerLoaderService getContainerLoaderService() {
        return containerLoaderService;
    }

    //    public Lock getLock() {
//        return lock;
//    }

}
