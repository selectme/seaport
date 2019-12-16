package by.epam.learn.mudrahelau.states;

/**
 * Possible states of {@link by.epam.learn.mudrahelau.model.Ship}.
 *
 * @author Viktar on 09.12.2019
 */
public enum  ShipState {
    /**
     * Means that {@link by.epam.learn.mudrahelau.model.Ship} has sailed for loading containers.
     */
    ON_LOAD,
    /**
     * Means that {@link by.epam.learn.mudrahelau.model.Ship} has sailed for unloading containers.
     */
    ON_UNLOAD
}
