package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SimpleValueExpression implements IExpression{

    private final List<AnimalModel> list;

    @Override
    public List<AnimalModel> evaluate() {
        return list;
    }

    public void add(AnimalModel animalModel){
        list.add(animalModel);
    }

}
