package by.epam.learn.mudrahelau.model;


import by.epam.learn.mudrahelau.states.ShipState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 09.12.2019
 */
public class Ship implements Callable<ShipReport> {

    private Long id;
    private int capacity;
    private List<Container> containersWarehouse;
    private ShipState shipState;

    private Lock lock = new ReentrantLock(true);

    public Ship(Long id, int capacity, List<Container> containersOnBoard, ShipState shipState) {
        this.id = id;
        this.capacity = capacity;
        this.containersWarehouse = containersOnBoard;
        this.shipState = shipState;
    }

    @Override
    public ShipReport call() throws InterruptedException {
        SeaPort port = SeaPort.getInstance();
        return port.shipService(this);
    }

    public Long getId() {
        return id;
    }

    public Lock getLock() {
        return lock;
    }


    public List<Container> getContainersWarehouse() {
        return containersWarehouse;
    }

    public void loadContainer(Container container) {
        containersWarehouse.add(container);
    }

    public void unloadContainer(Container container) {
        containersWarehouse.remove(container);
    }

    public int getCapacity() {
        return capacity;
    }

    public ShipState getShipState() {
        return shipState;
    }

    public void setShipState(ShipState shipState) {
        this.shipState = shipState;
    }


    public boolean hasFreeSpace() {
        return containersWarehouse.size() < capacity;
    }

    public boolean hasContainers() {
        return containersWarehouse.size() > 0;
    }


}
