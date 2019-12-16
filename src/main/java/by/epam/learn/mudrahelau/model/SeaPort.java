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
 * Object model that represents a sea port.
 *
 * @author Viktar on 09.12.2019
 */

public class SeaPort {

    /**
     * List of {@link Pier} in the sea port.
     */
    private List<Pier> piers = new ArrayList<>();
    /**
     * Static instance.
     */
    private static final SeaPort SEAPORT_INSTANCE = new SeaPort();
    private static final Logger LOGGER = LogManager.getLogger(SeaPort.class);
    private Lock lock = new ReentrantLock(true);

    /**
     * {@link Ship}(threads) goes at the sea where containers are loaded on the ship or unloaded from the ship.
     * After entering the port {@link Ship} start searching free {@link Pier} where {@link Ship} will be processed.
     *
     * @param ship {@link Ship}
     * @return {@link ShipReport}
     */
    public ShipReport shipService(Ship ship) {

        Pier freePier;
        try {
            lock.lock();
            LOGGER.info("> Ship #" + ship.getId() + " is looking for a free pier...");
            freePier = PierService.getPierService().getFreePier(ship);
        } finally {
            lock.unlock();
        }

        LOGGER.info("Ship #" + ship.getId() + " has found a free pier...");

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
