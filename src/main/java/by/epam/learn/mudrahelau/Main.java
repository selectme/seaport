package by.epam.learn.mudrahelau;

import by.epam.learn.mudrahelau.handler.ShipsParametersHandler;
import by.epam.learn.mudrahelau.model.*;
import by.epam.learn.mudrahelau.states.PierState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Viktar on 09.12.2019
 */
public class Main {
    private static final String FILEPATH = "data/ships.txt";
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        SeaPort port = SeaPort.getInstance();

        Pier pier = new Pier(1L, PierState.FREE);
        Pier pier2 = new Pier(2L, PierState.FREE);
        Pier pier3 = new Pier(3L, PierState.FREE);

        port.addPier(pier);
        port.addPier(pier2);
        port.addPier(pier3);


        List<Ship> ships = ShipsParametersHandler.getShipsList(FILEPATH);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<ShipReport>> futures = new ArrayList<>();

        for (Ship shipReport : ships) {
            Future<ShipReport> reportFuture = executorService.submit(shipReport);
            futures.add(reportFuture);
        }

        executorService.shutdown();


        for (Future<ShipReport> future : futures) {
            System.out.println(future.get().toString());
        }


        for (Ship ship : ships) {
            LOGGER.info("Ship " + ship.getId() + " containers: " + ship.getContainersWarehouse().size());
        }

        LOGGER.info("warehouse containers: " + Warehouse.getInstance().getContainersWarehouse().size());

    }
}
