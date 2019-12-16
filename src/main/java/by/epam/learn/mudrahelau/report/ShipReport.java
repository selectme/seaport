package by.epam.learn.mudrahelau.report;

/**
 * The report that {@link by.epam.learn.mudrahelau.model.Ship} was processed. Includes the report {@see LoaderReport}
 * about quantity of containers which were loaded or unloaded on {@link by.epam.learn.mudrahelau.model.Ship}
 *
 * @author Viktar on 09.12.2019
 */
public class ShipReport {

    /**
     * {@link by.epam.learn.mudrahelau.model.Ship} identification number.
     */
    private long shipId;

    /**
     * {@link LoaderReport}
     */
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
