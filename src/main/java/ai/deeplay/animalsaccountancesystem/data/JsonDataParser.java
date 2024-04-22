package ai.deeplay.animalsaccountancesystem.data;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * Parses JSON data to create a list of AnimalModel objects and implements the IDataParser interface.
 * <p\>
 * JSON example <p\>
 * [{
 *     "id": "12",
 *     "owner": "Wilson!!!",
 *     "name": "Kenny",
 *     "weight": "42"
 * },<p\>
 * {
 *    "id": "13",
 *    "owner": "Wilson!!!",
 *    "name": "Kenny",
 *    "weight": "42"
 *  }
 * ]
 * */
@Component
public class JsonDataParser implements IDataParser {

    Long id;

    /**
     * Parses a JSON string to create a list of AnimalModel objects.
     *
     * @param jsonStr The JSON string to parse
     * @return A list of AnimalModel objects created from the JSON data.
     */
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

    /**
     * Builds an AnimalModel object from a JSONObject. Assigns the lastId as the ID.
     *
     * @param jsonObject The JSONObject from which to build the AnimalModel object.
     * @param lastId     The last assigned ID to use for the new object.
     * @return The AnimalModel object built from the JSONObject.
     */
    private AnimalModel buildAnimalModelFromJsonObject(JSONObject jsonObject, Long lastId) {
        AnimalModel animalModel = new AnimalModel(lastId);
        jsonObject
                .keySet()
                .forEach(key -> animalModel.setProperty(key, jsonObject.getString(key)));
        return animalModel;
    }

    /**
     * Sets the starting ID for creating AnimalModel objects from JSON data.
     *
     * @param id The starting ID value to set.
     */
    public void setStartId(Long id){
        this.id = id;
    }
}
