package by.epam.learn.mudrahelau.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Viktar on 13.12.2019
 */
public class InputParametersReader {

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
