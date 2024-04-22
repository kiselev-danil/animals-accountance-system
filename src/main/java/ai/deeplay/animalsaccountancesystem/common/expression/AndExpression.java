package ai.deeplay.animalsaccountancesystem.common.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an AND logical expression that combines two expressions.
 */
@RequiredArgsConstructor
public class AndExpression implements IExpression {

    private final IExpression leftExpression;
    private final IExpression rightExpression;

    /**
     * Evaluates the logical AND expression by evaluating the left and right expressions,
     * and returning the intersection of their results.
     *
     * @return The list of AnimalModel objects that satisfy both left and right expressions.
     */
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
