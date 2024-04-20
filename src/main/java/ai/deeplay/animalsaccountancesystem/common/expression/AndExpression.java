package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AndExpression implements IExpression {

    private final IExpression leftExpression;
    private final IExpression rightExpression;

    @Override
    public List<AnimalModel> evaluate() {
        List<AnimalModel> left = leftExpression.evaluate();
        List<AnimalModel> right = rightExpression.evaluate();
        return left.stream()
                .distinct()
                .filter(right::contains)
                .collect(Collectors.toList());
    }
}
