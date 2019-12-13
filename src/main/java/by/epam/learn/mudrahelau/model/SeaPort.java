package by.epam.learn.mudrahelau.model;

import by.epam.learn.mudrahelau.states.PierState;
import by.epam.learn.mudrahelau.states.ShipState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 09.12.2019
 */


public class SeaPort {
    private static SeaPort seaPort;
    private Lock lock = new ReentrantLock(true);
    private int counter = 0;
    private List<Pier> piers = new ArrayList<>();
    private Warehouse warehouse = Warehouse.getInstance();
    private ContainerLoader containerLoader;

    private static final SeaPort SEAPORT_INSTANCE = new SeaPort();

    public static SeaPort getInstance() {
        return SEAPORT_INSTANCE;
    }


    public ShipReport shipService(Ship ship) throws InterruptedException {

        // три потока(корабли) одновременно заходят в порт

        findFreePier(ship);

        return new ShipReport(ship.getId(), counter);
    }

    public void findFreePier(Ship ship) {
        Pier occupiedPier = null;
        boolean flag = true;
        System.out.println("> Ship #" + ship.getId() + " is looking for free pier...");
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
        try {
            occupiedPier.getLock().lock();

            startLoaderWork(ship, occupiedPier);

        } finally {
            occupiedPier.getLock().unlock();
        }

    }


    public LoaderReport startLoaderWork(Ship ship, Pier pier) {
        containerLoader = ContainerLoader.getInstance();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //  если у корабля статус "На разгрузку", то перекидываем контейнеры с корабля на склад.
        if (ship.getShipState() == ShipState.ON_UNLOAD) {

            containerLoader.unloadContainersFromShip(ship);

        } else if (ship.getShipState() == ShipState.ON_LOAD) {
            //здесь будет погрузка на корабль
            containerLoader.loadContainersToShip(ship);
        }

        pier.setPierState(PierState.FREE);

        return new LoaderReport(ship.getId());
    }


    public void addPier(Pier pier) {
        piers.add(pier);
    }

}
