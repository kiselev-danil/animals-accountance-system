package ai.deeplay.animalsaccountancesystem.common;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AnimalModel {
    private final Long id;
    private Map<String, String> properties;

    public void setProperty(String propertyName, String propertyValue) {
        if(properties == null) {
            properties = new HashMap<>();
        }
        properties.put(propertyName, propertyValue);
    }

    public String getProperty(String propertyName){
        return properties.get(propertyName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        AnimalModel that = (AnimalModel) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
