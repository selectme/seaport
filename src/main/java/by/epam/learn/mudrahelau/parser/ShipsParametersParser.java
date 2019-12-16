package by.epam.learn.mudrahelau.parser;

import by.epam.learn.mudrahelau.model.Container;
import by.epam.learn.mudrahelau.model.Ship;
import by.epam.learn.mudrahelau.reader.InputParametersReader;
import by.epam.learn.mudrahelau.states.ShipState;

import java.util.ArrayList;
import java.util.List;

/**
 * Ship parameter's parser needed for creating a ship.
 *
 * @author Viktar on 13.12.2019
 */
public class ShipsParametersParser {

    private static final String SPLITTER = "\\|";

    /**
     * Gets ship's list. The method parses parameters on the basis of which creates {@link Ship} and puts it in the list.
     * The file which will be parsed contains information about {@link Ship} ID, capacity,
     * containers on the board and {@link Ship} {@link ShipState}.
     * The most important that if {@link Ship} has {@link ShipState} ON_UNLOAD it means that {@link Ship} contains containers on the board.
     * The method checks parameters and if necessary {@link Ship} will be filled with containers.
     *
     * @param filepath Path to the file with {@link Ship} parameters
     * @return list of {@link Ship}
     */
    public static List<Ship> getShipsList(String filepath) {

        List<String> parameters = InputParametersReader.shipsParameters(filepath);

        List<Ship> ships = new ArrayList<>();

        for (String line : parameters.subList(1, parameters.size())) {
            String[] params = line.split(SPLITTER);

            int containersOnBoard = Integer.parseInt(params[2].trim());

            List<Container> containers = new ArrayList<>();

            if (isNeedToFillTheShipWithContainers(containersOnBoard)) {
                createContainers(containers, containersOnBoard);
            }

            long id = Long.valueOf(params[0].trim());
            int containersCapacity = Integer.parseInt(params[1].trim());
            ShipState shipState = ShipState.valueOf(params[3].trim());
            ships.add(new Ship(id, containersCapacity, containers, shipState));
        }
        return ships;
    }

    /**
     * Checks is filling {@link Ship} with containers is necessary.
     * @param quantity quantity of containers on {@link Ship}. If more than zero it means that {@link Ship} contains containers.
     * @return {@code true} if a {@link Ship} contains containers.
     * {@code false} if {@link Ship} does not contain containers.
     */
    private static boolean isNeedToFillTheShipWithContainers(int quantity) {
        return quantity > 0;
    }


    /**
     * Fills {@link Ship} with containers.
     *
     * @param emptyList List which will be filled with containers
     * @param quantity Quantity of containers which will be created.
     */
    private static void createContainers(List<Container> emptyList, int quantity) {
        for (int i = 0; i < quantity; i++) {
            emptyList.add(new Container());
        }
    }
}
