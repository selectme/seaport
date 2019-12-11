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
    private int containerCapacity;
    private SeaPort port;
    private List<Container> containersWarehouse = new ArrayList<>();
    private ShipState shipState;

    private Lock lock = new ReentrantLock(true);

    public Ship(Long id, int containerCapacity, ShipState shipState,  SeaPort port) {
        this.id = id;
        this.containerCapacity = containerCapacity;
        this.shipState = shipState;
        this.port = port;
    }

    public Long getId() {
        return id;
    }

    public Lock getLock() {
        return lock;
    }

    @Override
    public ShipReport call() throws InterruptedException {
        return port.shipService(this);
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

    public ShipState getShipState() {
        return shipState;
    }

    public void setShipState(ShipState shipState) {
        this.shipState = shipState;
    }
}
