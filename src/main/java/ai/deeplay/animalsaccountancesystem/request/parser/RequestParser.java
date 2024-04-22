package ai.deeplay.animalsaccountancesystem.request.parser;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import ai.deeplay.animalsaccountancesystem.common.expression.IExpression;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface RequestParser {

    IExpression proceedRequest(String request);

    public void setData(List<AnimalModel> data);
}
