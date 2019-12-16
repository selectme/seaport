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
    private static final int WAREHOUSE_CAPACITY = 8;
    private List<Container> containersWarehouse = new ArrayList<>();


    public static Warehouse getInstance() {
        return WAREHOUSE_INSTANCE;
    }

    public void loadContainer(Container container) {
        containersWarehouse.add(container);
    }

    public void unloadContainer(Container container) {
        containersWarehouse.remove(container);
    }

   public int getWarehouseCapacity() {
        return WAREHOUSE_CAPACITY;
    }

    public List<Container> getContainersWarehouse() {
        return containersWarehouse;
    }



    public boolean hasFreeSpace() {
        return containersWarehouse.size() < WAREHOUSE_CAPACITY;
    }


    public boolean hasContainers() {
        return containersWarehouse.size() > 0;
    }


    public Lock getLock() {
        return lock;
    }
}
