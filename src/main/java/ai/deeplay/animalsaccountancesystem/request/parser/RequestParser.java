package ai.deeplay.animalsaccountancesystem.request.parser;

import ai.deeplay.animalsaccountancesystem.common.expression.IExpression;
import org.springframework.stereotype.Component;



@Component
public interface RequestParser {

    IExpression proceedRequest(String request);
}
