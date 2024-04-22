package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

/**
 * Represents an equals comparison expression that checks for equality between a property of AnimalModel objects and a criteria.
 */
@Getter
@AllArgsConstructor
public class EqualsExpression implements IExpression {
    private IExpression data;
    private String property;
    private Object criteria;

    /**
     * Evaluates the equals expression by filtering the data based on property equality criteria.
     *
     * @return The list of AnimalModel objects that satisfy the equality criteria for the specified property.
     */
    @Override
    public List<AnimalModel> evaluate() {
        return data.evaluate()
                .stream()
                .filter(animalModel -> {
                    if (property.equals("id")) {
                        return animalModel.getId()
                                .equals(criteria);
                    } else {
                        return propertyNotNull(animalModel) && propertyEqualsCriteria(animalModel);
                    }
                })
                .toList();
    }

    private boolean propertyNotNull(AnimalModel animalModel) {
        return animalModel.getProperty(property) != null;
    }

    private boolean propertyEqualsCriteria(AnimalModel animalModel) {
        return animalModel.getProperty(property)
                .equals(criteria);
    }

}
