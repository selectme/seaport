package by.epam.learn.mudrahelau.model;

import by.epam.learn.mudrahelau.report.LoaderReport;
import by.epam.learn.mudrahelau.report.ShipReport;
import by.epam.learn.mudrahelau.service.ContainerLoaderService;
import by.epam.learn.mudrahelau.service.PierService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 09.12.2019
 */


public class SeaPort {
    private Lock lock = new ReentrantLock(true);
    private List<Pier> piers = new ArrayList<>();

    private static final Logger LOGGER = LogManager.getLogger(SeaPort.class);
    private static final SeaPort SEAPORT_INSTANCE = new SeaPort();


    public ShipReport shipService(Ship ship) throws InterruptedException {

        Pier freePier;
        try {
            lock.lock();
            LOGGER.info("> Ship #" + ship.getId() + " is looking for free pier...");
            freePier = PierService.getPierService().getFreePier(ship);
        } finally {
            lock.unlock();
        }

        LOGGER.info("> Ship #" + ship.getId() + " has found free pier...");

        LoaderReport report = new ContainerLoaderService().startLoaderWork(ship, freePier);
        return new ShipReport(ship.getId(), report);
    }

    public void addPier(Pier pier) {
        piers.add(pier);
    }

    public static SeaPort getInstance() {
        return SEAPORT_INSTANCE;
    }

    public List<Pier> getPiers() {
        return piers;
    }
}
