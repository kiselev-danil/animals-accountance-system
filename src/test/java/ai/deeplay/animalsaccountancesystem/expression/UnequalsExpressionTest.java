package ai.deeplay.animalsaccountancesystem.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import ai.deeplay.animalsaccountancesystem.common.expression.SimpleValueExpression;
import ai.deeplay.animalsaccountancesystem.common.expression.UnequalsExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnequalsExpressionTest {
    @Test
    void testEqualsExpressionBothHaveProperty() {
        AnimalModel m1 = new AnimalModel(1L, new HashMap<>());
        m1.setProperty("weight", "42");
        AnimalModel m2 = new AnimalModel(2L, new HashMap<>());
        m2.setProperty("weight", "42");
        List<AnimalModel> list = new ArrayList<>();
        list.add(m1);
        list.add(m2);

        UnequalsExpression expression = new UnequalsExpression(new SimpleValueExpression(list), "weight","42");
        List<AnimalModel> result = expression.evaluate();
        Assertions.assertEquals(0, result.size());

    }

    @Test
    void testEqualsExpressionOnlyOneHaveProperty() {
        AnimalModel m1 = new AnimalModel(1L, new HashMap<>());
        m1.setProperty("weight", "42");
        AnimalModel m2 = new AnimalModel(2L, new HashMap<>());
        m2.setProperty("weight", "terrific");
        AnimalModel m3 = new AnimalModel(3L, new HashMap<>());
        List<AnimalModel> list = new ArrayList<>();
        list.add(m1);
        list.add(m2);
        list.add(m3);

        UnequalsExpression expression = new UnequalsExpression(new SimpleValueExpression(list), "weight","42");
        List<AnimalModel> result = expression.evaluate();
        Assertions.assertEquals(2, result.size());

    }
}
