package by.epam.learn.mudrahelau.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Model object that represents the warehouse.
 *
 * @author Viktar on 09.12.2019
 */
public class Warehouse {
    private Lock lock = new ReentrantLock(true);
    /**
     * Static instance.
     */
    private static final Warehouse WAREHOUSE_INSTANCE = new Warehouse();
    /**
     * Warehouse capacity.
     */
    private static final int WAREHOUSE_CAPACITY = 8;
    /**
     * Containers in the warehouse.
     */
    private List<Container> containersWarehouse = new ArrayList<>();

    public void loadContainer(Container container) {
        containersWarehouse.add(container);
    }

    public void unloadContainer(Container container) {
        containersWarehouse.remove(container);
    }

    /**
     * Checks that the warehouse has free space.
     *
     * @return {@code true} - warehouse has free space
     * {@code false} - warehouse is full
     */
    public boolean hasFreeSpace() {
        return containersWarehouse.size() < WAREHOUSE_CAPACITY;
    }

    /**
     * Checks that warehouse has containers which can be loaded on a ship
     *
     * @return {@code true} - warehouse has containers
     * {@code false} - warehouse is empty.
     */
    public boolean hasContainers() {
        return containersWarehouse.size() > 0;
    }

    public static Warehouse getInstance() {
        return WAREHOUSE_INSTANCE;
    }


    public int getWarehouseCapacity() {
        return WAREHOUSE_CAPACITY;
    }

    public List<Container> getContainersWarehouse() {
        return containersWarehouse;
    }

    public Lock getLock() {
        return lock;
    }
}
