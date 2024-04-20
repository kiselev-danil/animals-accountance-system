package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UnequalsExpression implements IExpression {
    private IExpression data;
    private String property;
    private Object criteria;


    @Override
    public List<AnimalModel> evaluate() {
        return data
                .evaluate()
                .stream()
                .filter(animalModel -> {
                    if (property.equals("id")) {
                        return animalModel.getId().equals(criteria);
                    } else {
                        return propertyIsNull(animalModel) || propertyNotEqualsCriteria(animalModel);
                    }
                })
                .toList();
    }

    private boolean propertyIsNull(AnimalModel animalModel){
        return animalModel.getProperty(property) == null;
    }

    private boolean propertyNotEqualsCriteria(AnimalModel animalModel){
        return !animalModel.getProperty(property).equals(criteria);
    }
}
