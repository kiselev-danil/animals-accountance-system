package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Represents a simple value expression that holds a list of AnimalModel objects.
 */
@AllArgsConstructor
public class SimpleValueExpression implements IExpression{

    private final List<AnimalModel> list;

    /**
     * @return The list of AnimalModel objects in the expression.
     */
    @Override
    public List<AnimalModel> evaluate() {
        return list;
    }

    /**
     * Adds an AnimalModel object to the list held by the simple value expression.
     *
     * @param animalModel The AnimalModel object to add to the list.
     */
    public void add(AnimalModel animalModel){
        list.add(animalModel);
    }

}
