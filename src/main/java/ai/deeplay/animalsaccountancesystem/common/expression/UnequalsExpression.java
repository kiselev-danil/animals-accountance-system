package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Represents an unequals comparison expression that checks for inequality between a property of AnimalModel objects and a criteria.
 */
@Getter
@AllArgsConstructor
public class UnequalsExpression implements IExpression {
    private IExpression data;
    private String property;
    private Object criteria;


    /**
     * Evaluates the unequals expression by filtering the data based on property inequality criteria.
     *
     * @return The list of AnimalModel objects that do not satisfy the criteria for the specified property.
     */
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
