package by.epam.learn.mudrahelau.handler;

import by.epam.learn.mudrahelau.model.Container;
import by.epam.learn.mudrahelau.model.Ship;
import by.epam.learn.mudrahelau.parser.InputParametersParser;
import by.epam.learn.mudrahelau.states.ShipState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Viktar on 13.12.2019
 */
public class ShipsParametersHandler {

    private static final String SPLITTER = "\\|";

    public static List<Ship> getShipsList(String filepath) {

        List<String> parameters = InputParametersParser.shipsParameters(filepath);

        List<Ship> ships = new ArrayList<>();

        for (String line : parameters.subList(1, parameters.size())) {
            String[] params = line.split(SPLITTER);

            int containersOnBoard = Integer.parseInt(params[2].trim());

            List<Container> containers = new ArrayList<>();
            if (containersOnBoard > 0) {
                for (int i = 0; i < containersOnBoard; i++) {
                    containers.add(new Container());
                }
            }
            long id = Long.valueOf(params[0].trim());
            int containersCapacity = Integer.parseInt(params[1].trim());
            ShipState shipState = ShipState.valueOf(params[3].trim());
            ships.add(new Ship(id, containersCapacity, containers, shipState));
        }
        return ships;
    }
}
