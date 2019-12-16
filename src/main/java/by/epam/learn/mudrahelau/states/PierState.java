package by.epam.learn.mudrahelau.states;

/**
 * Possible states of {@link by.epam.learn.mudrahelau.model.Pier}
 *
 * @author Viktar on 09.12.2019
 */
public enum PierState {
    /**
     * Means that {@link by.epam.learn.mudrahelau.model.Pier} is free and {@link by.epam.learn.mudrahelau.model.Ship}
     * can occupy it.
     */
    FREE,
    /**
     * Means that {@link by.epam.learn.mudrahelau.model.Pier} is occupied and other {@link by.epam.learn.mudrahelau.model.Ship}
     * can't occupy it.
     */
    OCCUPIED
}
