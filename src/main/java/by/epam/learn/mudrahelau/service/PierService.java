package by.epam.learn.mudrahelau.service;

import by.epam.learn.mudrahelau.model.Pier;
import by.epam.learn.mudrahelau.model.SeaPort;
import by.epam.learn.mudrahelau.model.Ship;
import by.epam.learn.mudrahelau.states.PierState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Viktar on 16.12.2019
 */
public class PierService {

    private static final PierService pierService = new PierService();
    private SeaPort seaPort = SeaPort.getInstance();

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
