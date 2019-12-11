package by.epam.learn.mudrahelau;

import by.epam.learn.mudrahelau.model.*;
import by.epam.learn.mudrahelau.states.PierState;
import by.epam.learn.mudrahelau.states.ShipState;

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

        Ship ship = new Ship(1L, 5, ShipState.ON_UNLOAD, port);
        Ship ship2 = new Ship(2L, 5, ShipState.ON_UNLOAD, port);
        Ship ship3 = new Ship(3L, 5, ShipState.ON_UNLOAD, port);
        Ship ship4 = new Ship(4L, 5, ShipState.ON_UNLOAD, port);
        Ship ship5 = new Ship(5L, 5,  ShipState.ON_LOAD,port);

        List<Ship> ships = new ArrayList<>();
        ships.add(ship);
        ships.add(ship2);
        ships.add(ship3);
        ships.add(ship4);
        ships.add(ship5);

        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 6; j++) {
                ships.get(i).getContainersWarehouse().add(new Container());
            }
        }


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<ShipReport>> futures = new ArrayList<>();

        Future<ShipReport> reportFuture = executorService.submit(ship);
        Future<ShipReport> reportFuture2 = executorService.submit(ship2);
        Future<ShipReport> reportFuture3 = executorService.submit(ship3);
        Future<ShipReport> reportFuture4 = executorService.submit(ship4);
        Future<ShipReport> reportFuture5 = executorService.submit(ship5);
        futures.add(reportFuture);
        futures.add(reportFuture2);
        futures.add(reportFuture3);
        futures.add(reportFuture4);
        futures.add(reportFuture5);

        executorService.shutdown();


        for (Future<ShipReport> future : futures) {
            System.out.println(future.get().toString());
        }


        System.out.println("warehouse containers: " + Warehouse.getInstance().getContainersWarehouse().size());
        System.out.println("ship containers: " + ship.getContainersWarehouse().size());
        System.out.println("ship2 containers: " + ship2.getContainersWarehouse().size());
        System.out.println("ship3 containers: " + ship3.getContainersWarehouse().size());
        System.out.println("ship4 containers: " + ship4.getContainersWarehouse().size());
        System.out.println("ship5 containers: " + ship5.getContainersWarehouse().size());

    }
}
