package ai.deeplay.animalsaccountancesystem.data.io;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
/**
 * Reads data from a JSON file and implements the IDataReader interface.
 */
@Setter
@Getter
@Component
public class JsonFileReader implements IDataReader {

    private Path filePath;


    /**
     * Reads the content from the JSON file at the specified file path.
     *
     * @return The content of the JSON file as a single string.
     * @throws RuntimeException if the file path is null or an IO Exception occurs during reading.
     */
    @Override
    public String read() {
        if (filePath == null) {
            throw new RuntimeException("Wrong filePath parameter");
        }
        List<String> list;

        try {
            list = Files.readAllLines(filePath);
        }
        catch(IOException exception) {
            throw new RuntimeException("IO Exception occurred");
        }

        StringBuilder sb = new StringBuilder();
        for (String str : list ){
            sb.append(str);
        }
        return sb.toString();
    }
}
