package by.epam.learn.mudrahelau;

import by.epam.learn.mudrahelau.handler.ShipsParametersHandler;
import by.epam.learn.mudrahelau.model.*;
import by.epam.learn.mudrahelau.states.PierState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Viktar on 09.12.2019
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        SeaPort port = SeaPort.getInstance();

        Pier pier = new Pier(1L, PierState.FREE);
        Pier pier2 = new Pier(2L, PierState.FREE);
        Pier pier3 = new Pier(3L, PierState.FREE);

        port.addPier(pier);
        port.addPier(pier2);
        port.addPier(pier3);


        List<Ship> ships = ShipsParametersHandler.getShipsList("data/ships.txt");

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
            System.out.println("Ship " + ship.getId() + " containers: " + ship.getContainersWarehouse().size());
        }

        System.out.println("warehouse containers: " + Warehouse.getInstance().getContainersWarehouse().size());

    }
}
