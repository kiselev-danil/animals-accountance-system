package ai.deeplay.animalsaccountancesystem.common;

import lombok.*;

import java.util.Map;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AnimalModel {
    private final Long id;
    private Map<String, String> properties;

    public void setProperty(String propertyName, String propertyValue){
        properties.put(propertyName, propertyValue);
    }

    public String getProperty(String propertyName){
        return properties.get(propertyName);
    }

}
