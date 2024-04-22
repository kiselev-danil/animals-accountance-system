package ai.deeplay.animalsaccountancesystem.data;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * JSON example
 * [{
 *     id: "12",
 *     owner: "Wilson!!!"
 *     name: "Kenny"
 *     weight: "42"
 * },
 * {
 *    id: "13",
 *    owner: "Wilson!!!"
 *    name: "Kenny"
 *    weight: "42"
 *  }
 * ]
 * */
@Component
public class JsonDataParser implements IDataParser {

    Long id;

    @Override
    public List<AnimalModel> dataFromString(String jsonStr) {
        if (id == null){
            id = 0L;
        }
        List<AnimalModel> result = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            var model = buildAnimalModelFromJsonObject(jsonObject, id);
            id++;
            result.add(model);
        }

        return result;
    }

    private AnimalModel buildAnimalModelFromJsonObject(JSONObject jsonObject, Long lastId) {
        AnimalModel animalModel = new AnimalModel(lastId);
        jsonObject
                .keySet()
                .forEach(key -> animalModel.setProperty(key, jsonObject.getString(key)));
        return animalModel;
    }

    public void setStartId(Long id){
        this.id = id;
    }
}
