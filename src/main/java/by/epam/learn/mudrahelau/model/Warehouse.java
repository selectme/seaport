package by.epam.learn.mudrahelau.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 09.12.2019
 */
public class Warehouse {
    Lock lock = new ReentrantLock(true);
    Condition condition = lock.newCondition();
    private static Warehouse warehouse;
    private static final int warehouseCapacity = 20;
    private List<Container> containersWarehouse = new ArrayList<>();


    public static Warehouse getInstance() {
        if (warehouse == null) {
            warehouse = new Warehouse();
        }
        return warehouse;
    }


    public static int getWarehouseCapacity() {
        return warehouseCapacity;
    }

    public List<Container> getContainersWarehouse() {
        return containersWarehouse;
    }

    public void loadContainer(Container container) {
        lock.lock();
        containersWarehouse.add(container);
        lock.unlock();
    }

    public void unloadContainer(Container container) {
        lock.lock();
        containersWarehouse.remove(container);
        lock.unlock();
    }

    public boolean hasFreeSpace() {
        return containersWarehouse.size() < warehouseCapacity;
    }

    public Lock getLock() {
        return lock;
    }
}
