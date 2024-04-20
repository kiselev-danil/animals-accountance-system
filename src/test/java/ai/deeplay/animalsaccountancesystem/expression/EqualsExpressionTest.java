package ai.deeplay.animalsaccountancesystem.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import ai.deeplay.animalsaccountancesystem.common.expression.EqualsExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EqualsExpressionTest {
    @Test
    void testEqualsExpressionBothHaveProperty() {
        AnimalModel m1 = new AnimalModel(1L, new HashMap<>());
        m1.setProperty("weight", "42");
        AnimalModel m2 = new AnimalModel(2L, new HashMap<>());
        m2.setProperty("weight", "42");
        List<AnimalModel> list = new ArrayList<>();
        list.add(m1);
        list.add(m2);

        EqualsExpression expression = new EqualsExpression(list, "weight","42");
        Object result = expression.evaluate();
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(2, ((List<?>) result).size());

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


        EqualsExpression expression = new EqualsExpression(list, "weight","42");
        Object result = expression.evaluate();
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(1, ((List<?>) result).size());
        Assertions.assertEquals(1L, ((AnimalModel)(((List<?>) result).getFirst())).getId());

    }
}
