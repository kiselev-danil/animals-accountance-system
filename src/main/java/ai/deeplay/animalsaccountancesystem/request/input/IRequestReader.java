package ai.deeplay.animalsaccountancesystem.request.input;

import java.nio.file.Path;
import java.util.List;

public interface IRequestReader {
    public List<String> read(Path path);
}
