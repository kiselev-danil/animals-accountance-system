package ai.deeplay.animalsaccountancesystem.request.parser;

import ai.deeplay.animalsaccountancesystem.common.AnimalModel;
import ai.deeplay.animalsaccountancesystem.common.expression.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
//        request = removeBraces(request);
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

    public String removeBraces(String request) {
        StringBuilder sb = new StringBuilder(request);
        if((sb.charAt(0) == '(') && (sb.charAt(sb.length()-1) == ')')) {
            sb.delete(0,1);
            sb.delete(sb.length()-1,sb.length());
        }
        return sb.toString();
    }

    public String removeOperatorFromLeftOperand(String operand, String operator) {
        return operand.trim().split(operator.trim() + "$")[0].trim();
    }

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

    public String extractPropertyName(String request) {
        return request.split(propertyCriteriaSplitChar+"")[0];
    }

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

    public String extractCriteria(String request, String propertyName) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(request.split("^"+propertyName+propertyCriteriaSplitChar + "{1}")).forEach(sb::append);
        sb.delete(0,1);
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }

    public boolean requestIsDataKeyword(String request) {
        String regularExpression = "^"+collectionName+"$";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(request.trim());
        return matcher.matches();
    }

    public boolean requestIsPropertyCriteria(String request) {
        String regularExpression = "^[a-zA-Z]+" + propertyCriteriaSplitChar + "\".+\"$";
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(request.trim());
        return matcher.matches();
    }



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

//    private List<IExpression> findSubRequest(String request) {
//        StringBuilder subRequest = new StringBuilder();
//        List<IExpression> expressions = new ArrayList<>();
//        int nestLvl = 0;
//        for (char ch : request.toCharArray()) {
//            if (ch == ')') {
//                nestLvl--;
//                if (nestLvl == 0) {
//                    expressions.add(proceedRequest(subRequest.toString()));
//                    subRequest.setLength(0);
//                }
//            }
//            if (nestLvl > 0) {
//                subRequest.append(ch);
//            }
//            if (ch == '(') {
//                nestLvl++;
//            }
//        }
//        return expressions;
//    }


}
