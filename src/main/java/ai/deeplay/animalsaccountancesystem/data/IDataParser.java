package ai.deeplay.animalsaccountancesystem.data;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;

import java.util.List;

public interface IDataParser {
    List<AnimalModel> dataFromString(String json);
}
