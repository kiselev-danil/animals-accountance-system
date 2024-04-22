package ai.deeplay.animalsaccountancesystem.request.parser;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import ai.deeplay.animalsaccountancesystem.common.expression.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Request example:
 * (animal != name:"The Cat") && (animal == type:"Cheshire cat"); (animal == type:"Cheshire cat") - sub-request
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PlainTextRequestsParser implements RequestParser {

    @Getter
    private List<AnimalModel> dataset;

    @Value("${requests.collection.keyword}")
    private String collectionName;

    @Value("${requests.operators.equals}")
    private String eqOperator;

    @Value("${requests.operators.unequals}")
    private String uneqOperator;

    @Value("${requests.operators.and}")
    private  String andOperator;

    @Value("${requests.operators.or}")
    private String orOperator;

    private char propertyCriteriaSplitChar = ':';



    @Override
    public IExpression proceedRequest(String request) {
        log.info("handling request:{}", request);
        if(requestIsDataKeyword(request)) {
            return new SimpleValueExpression(dataset);
        }

        boolean isUnnestedOperatorFound = false;
        int nestLvl = 0;
        StringBuilder unnested = new StringBuilder();
        StringBuilder leftOperand = new StringBuilder();
        StringBuilder rightOperand = new StringBuilder();
        String unnestedOperator = null;
        for (char ch : request.toCharArray()) {
            if (ch == ')') {
                nestLvl--;
            }
            if (nestLvl == 0) {
                unnested.append(ch);
                unnestedOperator = findUnnestedOperator(unnested.toString());
                if(unnestedOperator!=null){
                    isUnnestedOperatorFound = true;
                }
            }
            if(isUnnestedOperatorFound) {
                rightOperand.append(ch);
            }
            else{
                leftOperand.append(ch);
            }
            if (ch == '(') {
                nestLvl++;
            }
        }

        if(unnestedOperator == null) {
            log.error("Operator is null for request:{}", request);
            throw new RuntimeException("Unexpected operator value");
        }
        String left = removeBraces(removeOperatorFromLeftOperand(leftOperand.toString(), unnestedOperator));
        String right = removeBraces(rightOperand.toString().trim());
        if(requestIsPropertyCriteria(right)) {
            String propertyName = extractPropertyName(right);
            String criteria = extractCriteria(right, propertyName);
            return createAssertionExpression(proceedRequest(left.trim()), propertyName.trim(), criteria.trim(), unnestedOperator.trim());
        }
        else {
            return createLogicalExpression(proceedRequest(left.trim()), proceedRequest(right.trim()), unnestedOperator.trim());
        }
    }

    @Override
    public void setData(List<AnimalModel> data) {
        this.dataset = data;
    }

    /**
     * Removes leading and trailing braces from the given request string.
     *
     * @param request The request string from which to remove braces.
     * @return A string with leading and trailing braces removed.
     */
    private String removeBraces(String request) {
        StringBuilder sb = new StringBuilder(request);
        if((sb.charAt(0) == '(') && (sb.charAt(sb.length()-1) == ')')) {
            sb.delete(0,1);
            sb.delete(sb.length()-1,sb.length());
        }
        return sb.toString();
    }

    /**
     * Removes operator from the left operand based on the operator found in expression
     *
     * @param operand   The left operand as a string.
     * @param operator  The operator to be removed from the left operand.
     * @return A left operand string without operator
     */
    private String removeOperatorFromLeftOperand(String operand, String operator) {
        return operand.trim().split(operator.trim() + "$")[0].trim();
    }

    /**
     * Creates a logical expression based on the left and right expressions provided along with the operator.
     *
     * @param left     The left expression in the logical operation.
     * @param right    The right expression in the logical operation.
     * @param operator The logical operator (AND or OR).
     * @return An IExpression object representing the logical expression.
     */
    private IExpression createLogicalExpression(IExpression left, IExpression right, String operator) {
        if(operator.equals(andOperator)) {
            return new AndExpression(left, right);
        }
        else
        if(operator.equals(orOperator)) {
            return new OrExpression(left,right);
        }
        else throw new RuntimeException("Unexpected operator value");
    }

    /**
     * Extracts the property name from the given request based on a split character.
     *
     * @param request The request string from which to extract the property name.
     * @return The extracted property name.
     */
    private String extractPropertyName(String request) {
        return request.split(propertyCriteriaSplitChar+"")[0];
    }

    /**
     * Creates an assertion expression based on the input expression, property name, criteria, and operator.
     *
     * @param iExpression The input expression.
     * @param propertyName The property name.
     * @param criteria The criteria for assertion.
     * @param operator The operator for comparison (Equals or Not Equals).
     * @return An IExpression object representing the assertion expression.
     */
    private IExpression createAssertionExpression(IExpression iExpression, String propertyName, String criteria, String operator) {
        if(operator.equals(eqOperator)) {
            return new EqualsExpression(iExpression, propertyName, criteria);
        }
        else
        if(operator.equals(uneqOperator)) {
            return new UnequalsExpression(iExpression, propertyName, criteria);
        }
        else throw new RuntimeException("Unexpected operator value");
    }

    /**
     * Extracts the criteria from the request string based on the property name and split character.
     *
     * @param request      The request string from which to extract the criteria.
     * @param propertyName The property name used in the request string.
     * @return The extracted criteria.
     */
    private String extractCriteria(String request, String propertyName) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(request.split("^"+propertyName+propertyCriteriaSplitChar + "{1}")).forEach(sb::append);
        sb.delete(0,1);
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }

    /**
     * Checks if the request string is a data keyword
     *
     * @param request The request string to check
     * @return True if the request string is a data keyword, false otherwise.
     */
    private boolean requestIsDataKeyword(String request) {
        String regularExpression = "^"+collectionName+"$";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(request.trim());
        return matcher.matches();
    }

    /**
     * Checks if the request string represents a property criteria
     *
     * @param request The request string to check
     * @return True if the request string represents a property criteria, false otherwise.
     */
    private boolean requestIsPropertyCriteria(String request) {
        String regularExpression = "^[a-zA-Z]+" + propertyCriteriaSplitChar + "\".+\"$";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(request.trim());
        return matcher.matches();
    }

    /**
     * Finds the operator within the request string
     *
     * @param unnestedRequest The unnested request string to find the operator in.
     * @return The unnested operator found, or null if not .
     */
    private String findUnnestedOperator(String unnestedRequest) {
        String regularExpression = "\s(" + eqOperator + "|" + uneqOperator + "|" + andOperator + "|" + orOperator + ")\s";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(unnestedRequest);
        if (matcher.find()) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();
            return unnestedRequest.substring(startIndex, endIndex);
        } else {
            return null;
        }
    }
}
