package by.epam.learn.mudrahelau.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader for data from file.
 *
 * @author Viktar on 13.12.2019
 */
public class InputParametersReader {

    /**
     * Read parameters from file and puts them into the list.
     *
     * @param filePath path to the file.
     * @return list of parameters.
     */
    public static List<String> shipsParameters(String filePath) {
        List<String> parameters = new ArrayList<>();
        try {
            parameters = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parameters;
    }
}
