package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class OrExpression implements  IExpression{
    private final IExpression leftExpression;
    private final IExpression rightExpression;

    @Override
    public List<AnimalModel> evaluate() {
        List<AnimalModel> left = leftExpression.evaluate();
        List<AnimalModel> right = rightExpression.evaluate();
        return Stream.concat(left.stream(), right.stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
