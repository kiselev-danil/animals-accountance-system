package ai.deeplay.animalsaccountancesystem.expression;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import ai.deeplay.animalsaccountancesystem.common.expression.AndExpression;
import ai.deeplay.animalsaccountancesystem.common.expression.EqualsExpression;
import ai.deeplay.animalsaccountancesystem.common.expression.SimpleValueExpression;
import ai.deeplay.animalsaccountancesystem.common.expression.UnequalsExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AndExpressionTest {

    @Test
    public void testHalfIntersection(){
        SimpleValueExpression valueExpression = getSimpleValueExpression();



        EqualsExpression is42 = new EqualsExpression(valueExpression, "weight", "42");
        UnequalsExpression isNotKenny = new UnequalsExpression(valueExpression, "owner", "Kenny");
        AndExpression andExpression = new AndExpression(is42, isNotKenny);

        Assertions.assertEquals(3, is42.evaluate().size());
        Assertions.assertEquals(3, isNotKenny.evaluate().size());
        var result = andExpression.evaluate();
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(isAllPresented(Arrays.asList(1L,2L),result));

    }

    private boolean isAllPresented(List<Long> requiredIds, List<AnimalModel> animals) {
        for (AnimalModel am : animals) {
            if (!requiredIds.contains(am.getId())) {
                return false;
            }
        }
        return true;
    }

    private static SimpleValueExpression getSimpleValueExpression() {
        AnimalModel m1 = new AnimalModel(1L, new HashMap<>());
        m1.setProperty("weight", "42");
        m1.setProperty("owner", "Wilson!!!");
        AnimalModel m2 = new AnimalModel(2L, new HashMap<>());
        m2.setProperty("weight", "42");
        m2.setProperty("owner", "Wilson!!!");
        AnimalModel m3 = new AnimalModel(3L, new HashMap<>());
        m3.setProperty("weight", "false");
        m3.setProperty("owner", "Kenny");
        AnimalModel m4 = new AnimalModel(4L, new HashMap<>());
        m4.setProperty("weight", "42");
        m4.setProperty("owner", "Kenny");
        AnimalModel m5 = new AnimalModel(5L, new HashMap<>());
        m5.setProperty("weight", "true");
        m5.setProperty("owner", "Kenny");
        AnimalModel m6 = new AnimalModel(6L, new HashMap<>());
        m6.setProperty("weight", "not stated");
        m6.setProperty("owner", "Wilson!!!");
        SimpleValueExpression valueExpression = new SimpleValueExpression(new ArrayList<>());
        valueExpression.add(m1);
        valueExpression.add(m2);
        valueExpression.add(m3);
        valueExpression.add(m4);
        valueExpression.add(m5);
        valueExpression.add(m6);
        return valueExpression;
    }

}
