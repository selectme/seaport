package by.epam.learn.mudrahelau.model;

import java.time.LocalTime;

/**
 * @author Viktar on 09.12.2019
 */
public class Report {

    private long shipId;
    private int processingNumber;


    public Report(long shipId, int processingNumber) {
        this.shipId = shipId;
        this.processingNumber = processingNumber;

    }

    public long getShipId() {
        return shipId;
    }



    @Override
    public String toString() {
        return "Report: " +
                "Ship №" + shipId + " processed.";
    }
}
