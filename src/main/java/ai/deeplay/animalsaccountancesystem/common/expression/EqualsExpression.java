package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class EqualsExpression implements IExpression {
    private List<AnimalModel> data;
    private String property;
    private Object criteria;


    @Override
    public Object evaluate() {
        return data.stream()
                .filter(animalModel -> {
                    if (property.equals("id")) {
                        return animalModel.getId().equals(criteria);
                    } else {
                        return propertyNotNull(animalModel) && propertyEqualsCriteria(animalModel);
                    }
                })
                .toList();
    }

    private boolean propertyNotNull(AnimalModel animalModel){
        return animalModel.getProperty(property) != null;
    }

    private boolean propertyEqualsCriteria(AnimalModel animalModel){
        return animalModel.getProperty(property).equals(criteria);
    }

}
