package by.epam.learn.mudrahelau.model;

import by.epam.learn.mudrahelau.states.PierState;
import by.epam.learn.mudrahelau.states.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 09.12.2019
 */


public class SeaPort {
    private Lock lock = new ReentrantLock(true);
    private int counter = 0;
    private List<Pier> piers = new ArrayList<>();
    private Warehouse warehouse = Warehouse.getInstance();
    private ContainerLoader containerLoader = ContainerLoader.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(SeaPort.class);

    private static final SeaPort SEAPORT_INSTANCE = new SeaPort();

    public static SeaPort getInstance() {
        return SEAPORT_INSTANCE;
    }


    public ShipReport shipService(Ship ship) throws InterruptedException {

        //  потоки(корабли) одновременно заходят в порт
        Pier freePier;
        try {
            lock.lock();
            LOGGER.info("> Ship #" + ship.getId() + " is looking for free pier...");
            freePier = getFreePier(ship);

        } finally {
            lock.unlock();
        }
        LOGGER.info("> Ship #" + ship.getId() + " has found free pier...");
        LoaderReport report = startLoaderWork(ship, freePier);
        return new ShipReport(ship.getId(), counter, report);
    }

    public Pier getFreePier(Ship ship) {
        Pier occupiedPier = null;
        boolean flag = true;

        do {
            for (Pier pier : piers) {
                if (pier.getPierState() == PierState.FREE) {
                    pier.setPierState(PierState.OCCUPIED);
                    occupiedPier = pier;
                    flag = false;
                    break;
                }
            }
        } while (flag);
        return occupiedPier;
    }

    public LoaderReport startLoaderWork(Ship ship, Pier pier) {
        LOGGER.info("start work");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //  если у корабля статус "На разгрузку", то перекидываем контейнеры с корабля на склад.
        if (ship.getShipState() == ShipState.ON_UNLOAD) {
                containerLoader.getContainerLoaderService().unloadContainersFromShip(ship);
//            containerLoader.unloadContainersFromShip(ship);

        } else if (ship.getShipState() == ShipState.ON_LOAD) {
//            containerLoader.loadContainersToShip(ship);
            containerLoader.getContainerLoaderService().loadContainersToShip(ship);
        }

        pier.setPierState(PierState.FREE);

        return new LoaderReport(ship.getId());
    }


    public void addPier(Pier pier) {
        piers.add(pier);
    }

}
