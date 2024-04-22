package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents an OR logical expression that combines two expressions.
 */
@RequiredArgsConstructor
public class OrExpression implements  IExpression{
    private final IExpression leftExpression;
    private final IExpression rightExpression;

    /**
     * Evaluates the logical OR expression by evaluating the left and right expressions
     * and combining their results without duplicates.
     *
     * @return The list of AnimalModel objects that result from combining the left and right expression results.
     */
    @Override
    public List<AnimalModel> evaluate() {
        List<AnimalModel> left = leftExpression.evaluate();
        List<AnimalModel> right = rightExpression.evaluate();
        return Stream.concat(left.stream(), right.stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
