package by.epam.learn.mudrahelau.model;


import by.epam.learn.mudrahelau.report.ShipReport;
import by.epam.learn.mudrahelau.states.ShipState;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Model object that represents a Ship.
 *
 * @author Viktar on 09.12.2019
 */
public class Ship implements Callable<ShipReport> {
    /**
     * Ship's identification number.
     */
    private Long id;
    /**
     * Ship's capacity.
     */
    private int capacity;
    /**
     * Containers on the ship.
     */
    private List<Container> containersWarehouse;
    /**
     * {@link ShipState}
     */
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


    public void loadContainer(Container container) {
        containersWarehouse.add(container);
    }

    public void unloadContainer(Container container) {
        containersWarehouse.remove(container);
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
