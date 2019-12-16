package by.epam.learn.mudrahelau.service;

import by.epam.learn.mudrahelau.model.Pier;
import by.epam.learn.mudrahelau.model.SeaPort;
import by.epam.learn.mudrahelau.model.Ship;
import by.epam.learn.mudrahelau.states.PierState;


/**
 * @author Viktar on 16.12.2019
 */
public class PierService {
    /**
     * Static instance.
     */
    private static final PierService pierService = new PierService();
    /**
     * Instance of {@link SeaPort}.
     */
    private SeaPort seaPort = SeaPort.getInstance();

    /**
     * Trying to find a free {@link Pier} until for {@link Ship} until it happens. Then {@link Pier} gets status OCCUPIED
     * and other ships can not to occupy it until it gets status FREE.
     *
     * @param ship {@link Ship}
     * @return free {@link Pier}.
     */
    public Pier getFreePier(Ship ship) {
        Pier occupiedPier = null;
        boolean flag = true;
        do {
            for (Pier pier : seaPort.getPiers()) {
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

    public static PierService getPierService() {
        return pierService;
    }
}
