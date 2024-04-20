package ai.deeplay.animalsaccountancesystem.common;

import lombok.*;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        AnimalModel that = (AnimalModel) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
