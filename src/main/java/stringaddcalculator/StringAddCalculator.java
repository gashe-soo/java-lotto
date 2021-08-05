package stringaddcalculator;

import stringaddcalculator.util.StringUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAddCalculator {

    private static final String DELIMITER = ",|:";


    public static int splitAndSum(String expression) {

        if (StringUtil.isBlank(expression)) {
            return 0;
        }

        Matcher matchResult = Pattern.compile("//(.)\n(.*)").matcher(expression);
        if (matchResult.find()) {
            String customDelimiter = matchResult.group(1);
            String[] tokens= matchResult.group(2).split(customDelimiter);
            return sumSplitExpression(tokens);
        }

        return sumSplitExpression(expression.split(DELIMITER));
    }

    private static int sumSplitExpression(String[] splitExpression) {
        return Arrays.stream(splitExpression)
                .mapToInt(Integer::parseInt)
                .reduce(0, (total, number) -> total + number)
                ;
    }
}
