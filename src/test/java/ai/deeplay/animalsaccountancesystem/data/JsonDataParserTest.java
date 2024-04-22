package ai.deeplay.animalsaccountancesystem.data;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
@ActiveProfiles("test")
public class JsonDataParserTest {

    /*
    JSON example
[{
  id: 12,
  owner: "Wilson!!!",
  name: "Kenny",
  weight: "42"
}
]
*/
    @Test
    public void testSingleObj(){
        JsonDataParser jsonDataParser = new JsonDataParser();
        String inputJson = "[{\n" + "  id: \"12\",\n" + "  owner: \"Wilson!!!\",\n" + "  name: \"Kenny\",\n" + "  weight: \"42\"\n" + "}\n" + "]";
        AnimalModel res = jsonDataParser.dataFromString(inputJson).getFirst();
        Assertions.assertNotNull(res);
    }

    /*
JSON example
[{
external_id: "12",
owner: "Wilson!!!",
name: "Kenny",
weight: "42"
},
{
external_id: "12",
owner: "Wilson!!!",
name: "Kenny",
weight: "42"
}
]
*/
    @Test
    public void testManyObj(){
        JsonDataParser jsonDataParser = new JsonDataParser();
        String inputJson = "[{\n" + "external_id: \"12\",\n" + "status: \"alive\",\n" + "name: \"Kenny\",\n" + "weight: \"42\"\n" + "},\n" + "{\n" + "external_id: \"12\",\n" + "owner: \"Wilson!!!\",\n" + "name: \"Kenny\",\n" + "weight: \"42\"\n" + "}\n" + "]";
        List<AnimalModel> res = jsonDataParser.dataFromString(inputJson);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(2, res.size());
    }



}
