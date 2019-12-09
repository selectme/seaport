package by.epam.learn.mudrahelau.model;


import java.util.concurrent.Callable;

/**
 * @author Viktar on 09.12.2019
 */
public class Ship implements Callable<Report> {

    private Long id;
    private SeaPort port;

    public Ship(Long id, SeaPort port) {
        this.id = id;
        this.port = port;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Report call() throws InterruptedException {
        return port.shipService(this);
    }
}
