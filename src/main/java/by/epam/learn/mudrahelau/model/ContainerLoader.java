package by.epam.learn.mudrahelau.model;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 11.12.2019
 */
public class ContainerLoader {

    private Ship ship;
    private Lock lock = new ReentrantLock(true);

    public static final ContainerLoader instance = new ContainerLoader();

    public void loadContainerToShip(Ship ship, Container container){
        ship.loadContainer(container);
    }

    public void unloadContainerFromShip(Ship ship, Container container){
        ship.unloadContainer(container);
    }

    public Lock getLock() {
        return lock;
    }

}
