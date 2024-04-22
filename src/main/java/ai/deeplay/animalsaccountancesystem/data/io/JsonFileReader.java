package ai.deeplay.animalsaccountancesystem.data.io;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
/**
 * WARNING! May cause memory problem caused by String max length;
 * */
@Setter
@Getter
@Component
public class JsonFileReader implements IDataReader {

    private Path filePath;


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
