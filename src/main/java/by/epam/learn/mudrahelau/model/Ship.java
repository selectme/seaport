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
    private int containersOnBoard;
    private int capacity;
    private SeaPort port;
    private List<Container> containersWarehouse = new ArrayList<>();
    private ShipState shipState;

    private Lock lock = new ReentrantLock(true);

    public Ship(Long id, int capacity, List<Container> containersOnBoard, ShipState shipState) {
        this.id = id;
        this.capacity = capacity;
        this.containersWarehouse = containersOnBoard;
        this.shipState = shipState;
    }

    public Long getId() {
        return id;
    }

    public Lock getLock() {
        return lock;
    }

//    @Override
//    public ShipReport call() throws InterruptedException {
//        return port.shipService(this);
//    }

    @Override
    public ShipReport call() throws InterruptedException {
        port = SeaPort.getInstance();
        return port.shipService(this);
    }

    public List<Container> getContainersWarehouse() {
        return containersWarehouse;
    }

    public void loadContainer(Container container) {
//        lock.lock();
        containersWarehouse.add(container);
//        lock.unlock();
    }

    public void unloadContainer(Container container) {
//        lock.lock();
        containersWarehouse.remove(container);
//        lock.unlock();
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

    public int getContainersOnBoard() {
        return containersOnBoard;
    }

    public boolean hasFreeSpace() {
        return containersWarehouse.size() < capacity;
    }

    public boolean hasContainers() {
        return containersWarehouse.size() > 0;
    }

    public int numberOfContainersToLoad(){
       return this.capacity - this.containersWarehouse.size();
    }
}
