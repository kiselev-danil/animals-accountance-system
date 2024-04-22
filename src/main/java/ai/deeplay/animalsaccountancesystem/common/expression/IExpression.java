package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;

import java.util.List;

public interface IExpression {
    public List<AnimalModel> evaluate();
}
