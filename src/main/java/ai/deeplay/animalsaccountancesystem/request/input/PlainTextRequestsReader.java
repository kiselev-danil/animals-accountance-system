package ai.deeplay.animalsaccountancesystem.request.input;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class PlainTextRequestsReader implements IRequestReader {
    @Override
    public List<String> read(Path path) {
        if (path == null) {
            throw new RuntimeException("Wrong filePath parameter");
        }
        List<String> list;

        try {
            list = Files.readAllLines(path);
        } catch (IOException exception) {
            log.error("IO Exception occurred during Request file reading. File path {}", path.toAbsolutePath());
            throw new RuntimeException("IO Exception occurred during Request file reading");
        }

        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str);
        }
        String[] requests = sb.toString().split(";");

        return Arrays.stream(requests).toList();
    }

}
