package ai.deeplay.animalsaccountancesystem.request.parser;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import ai.deeplay.animalsaccountancesystem.common.expression.IExpression;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface RequestParser {
    /**
     * Processes a request and generates an expression based on the request string.
     *
     * @param request The request string to be processed.
     * @return An IExpression object based on the request.
     */
    IExpression proceedRequest(String request);

    /**
     * Sets the data for the AnimalModels.
     *
     * @param data The list of AnimalModel objects to set as the data.
     */
    public void setData(List<AnimalModel> data);
}
