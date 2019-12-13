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

    public static List<Ship> getShipsList(String filepath) {

        List<String> parameters = InputParametersParser.shipsParameters(filepath);

        List<Ship> ships = new ArrayList<>();

        for (String line : parameters.subList(1, parameters.size())) {
            String[] params = line.split("\\|");
            List<Container> containers = new ArrayList<>();
            int containersCapacity = Integer.parseInt(params[1].trim());
            int containersOnBoard = Integer.parseInt(params[2].trim());
            if (containersOnBoard > 0) {
                for (int i = 0; i < containersOnBoard; i++) {
                    containers.add(new Container());
                }
            }

            ships.add(new Ship(Long.valueOf(params[0].trim()), containersCapacity, containers, ShipState.valueOf(params[3].trim())));
        }
        return ships;
    }
}
