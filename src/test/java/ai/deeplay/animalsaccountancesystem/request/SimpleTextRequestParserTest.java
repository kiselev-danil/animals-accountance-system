package ai.deeplay.animalsaccountancesystem.request;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import ai.deeplay.animalsaccountancesystem.common.expression.AndExpression;
import ai.deeplay.animalsaccountancesystem.common.expression.EqualsExpression;
import ai.deeplay.animalsaccountancesystem.common.expression.IExpression;
import ai.deeplay.animalsaccountancesystem.common.expression.OrExpression;
import ai.deeplay.animalsaccountancesystem.request.parser.PlainTextRequestsParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class SimpleTextRequestParserTest {
    private final String collectionName = "animal";
    private final String eqOperator = "==";

    @Autowired
    PlainTextRequestsParser parser;

    @BeforeEach
    public void setUp(){
        parser.setData(getSimpleValueExpressionData());
    }

    @Test
    public void lineSplitTest(){
        String str = "aaa  !! bbb";
        var splitted = str.split(" ");
        Assertions.assertEquals(4, splitted.length);
    }

    @Test
    public void testUnnestedRequestParsed() {
        String property = "owner";
        String value = "\"Wilson!!!\"";
        String requestCriteria = property + ":" + value;
        String request = collectionName + " " + eqOperator + " " + requestCriteria;
        IExpression result = parser.proceedRequest(request);
        Assertions.assertInstanceOf(EqualsExpression.class, result);
        var resultData = result.evaluate();
        Assertions.assertEquals(3, resultData.size());
        Assertions.assertTrue(isAllPresented(Arrays.asList(1L, 2L, 6L), resultData));
    }

    @Test
    public void testUnnestedRequestParsedWithBraces() {
        String property = "owner";
        String value = "\"Wilson!!!\"";
        String requestCriteria = property + ":" + value;
        String request = "(" + collectionName + ") " + eqOperator + " " + requestCriteria;
        IExpression result = parser.proceedRequest(request);
        Assertions.assertInstanceOf(EqualsExpression.class, result);
        var resultData = result.evaluate();
        Assertions.assertEquals(3, resultData.size());
        Assertions.assertTrue(isAllPresented(Arrays.asList(1L, 2L, 6L), resultData));
    }

    @Test
    public void testNestedRequestSameDepthParsed() {
        String request = "(animal != weight:\"42\") && (animal == owner:\"Kenny\")";
        IExpression result = parser.proceedRequest(request);
        Assertions.assertInstanceOf(AndExpression.class, result);
        var resultData = result.evaluate();
        Assertions.assertEquals(2, resultData.size());
        Assertions.assertTrue(isAllPresented(Arrays.asList(3L,5L), resultData));
    }

    @Test
    public void testNestedRequestSameDepthParsedWithOR() {
        String request = "(animal != weight:\"42\") || (animal == owner:\"Kenny\")";
        IExpression result = parser.proceedRequest(request);
        Assertions.assertInstanceOf(OrExpression.class, result);
        var resultData = result.evaluate();
        Assertions.assertEquals(4, resultData.size());
        Assertions.assertTrue(isAllPresented(Arrays.asList( 3L, 5L, 6L, 4L), resultData));
    }

    @Test
    public void testNestedRequestDifferentDepthParsed() {
        String request = "(animal != weight:\"42\") == owner:\"Wilson!!!\"";
        IExpression result = parser.proceedRequest(request);
        Assertions.assertInstanceOf(EqualsExpression.class, result);
        var resultData = result.evaluate();
        Assertions.assertEquals(1, resultData.size());
        Assertions.assertTrue(isAllPresented(Arrays.asList(6L), resultData));
    }

    private boolean isAllPresented(List<Long> requiredIds, List<AnimalModel> animals) {
        for (AnimalModel am : animals) {
            if (!requiredIds.contains(am.getId())) {
                return false;
            }
        }
        return true;
    }


    private static List<AnimalModel> getSimpleValueExpressionData() {
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
        List<AnimalModel> valueExpression = new ArrayList<>();
        valueExpression.add(m1);
        valueExpression.add(m2);
        valueExpression.add(m3);
        valueExpression.add(m4);
        valueExpression.add(m5);
        valueExpression.add(m6);
        return valueExpression;
    }
}
