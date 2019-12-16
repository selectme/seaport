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
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();
    private static final Warehouse WAREHOUSE_INSTANCE = new Warehouse();
    private static final int warehouseCapacity = 15;
    private List<Container> containersWarehouse = new ArrayList<>();


    public static Warehouse getInstance() {
        return WAREHOUSE_INSTANCE;
    }


   public int getWarehouseCapacity() {
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

    public boolean hasFreeSpace(int numberOfContainersForLoad) {
        return (containersWarehouse.size() + numberOfContainersForLoad) <= warehouseCapacity;
    }

    public boolean hasNeededNumberContainers(int numberOfContainers) {
        return containersWarehouse.size() >= numberOfContainers;
    }

    public boolean hasContainers() {
        return containersWarehouse.size() > 0;
    }


    public Lock getLock() {
        return lock;
    }
}
