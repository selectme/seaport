package by.epam.learn.mudrahelau.model;

import java.time.LocalTime;

/**
 * @author Viktar on 09.12.2019
 */
public class ShipReport {

    private long shipId;

    private LoaderReport loaderReport;



    public ShipReport(long shipId, LoaderReport loaderReport) {
        this.shipId = shipId;
        this.loaderReport = loaderReport;
    }

    public long getShipId() {
        return shipId;
    }


    @Override
    public String toString() {
        return "ShipReport: " +
                "Ship #" + shipId + " processed. " + loaderReport.toString();
    }
}
