package by.epam.learn.mudrahelau;

import by.epam.learn.mudrahelau.model.Pier;
import by.epam.learn.mudrahelau.model.Report;
import by.epam.learn.mudrahelau.model.SeaPort;
import by.epam.learn.mudrahelau.model.Ship;
import by.epam.learn.mudrahelau.states.PierState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Viktar on 09.12.2019
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        SeaPort port = new SeaPort();
        Pier pier = new Pier(1L, PierState.FREE);
        Pier pier2 = new Pier(2L, PierState.FREE);
        Pier pier3 = new Pier(3L, PierState.FREE);


        port.addPier(pier);
        port.addPier(pier2);
        port.addPier(pier3);


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<Report>> futures = new ArrayList<>();
        for (long i = 1; i < 3; i++) {
            Future<Report> reportFuture = executorService.submit(new Ship(i, port));
            futures.add(reportFuture);
        }
        executorService.shutdown();

        for (Future<Report> future : futures) {
            System.out.println(future.get().toString());
        }
    }
}
