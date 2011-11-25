package ccalculator;
import java.text.ParseException;
import java.util.regex.*;

/**
 * Represents Operator token.
 * 
 * Supported operators are: +, -, *, /, ^
 */
public class Operator extends Token
{
	/**
	 * Contains character value of operator.
	 */
    private char value;
    
    /**
     * Contains operator grouped by their priorities.
     */
    private static char[][] priorities = {
        {'^'},
        {'*', '/'},
        {'+', '-'}
    };
    
    /**
     * Constructs Operator object by parsing the string.
     * 
     * @param string          String to parse
     * @throws ParseException Throws if unable to find an operator
     */
    public Operator(String str) throws ParseException
    {
        Matcher matcher = Pattern.compile("\\s*([\\+\\-\\*/\\^])").matcher(str);
        if (!matcher.lookingAt())
        {
            throw new ParseException("operator", offset);
        }
        value = matcher.group(1).charAt(0);
        length = matcher.end();
        offset += length;
    }
    
    /**
     * Returns the amount of priority groups.
     * @return The amount of priority groups
     */
    public static int total_priorities()
    {
        return priorities.length;
    }
    
    /**
     * Returns priority of the operator.
     * @return Priority of the operator
     */
    public int priority()
    {
        for (int i = 0; i < priorities.length; i++)
        {
            for (char operator: priorities[i])
            {
                if (operator == value) return priorities.length - i;
            }
        }
        return 0;
    }
    
    /**
     * Perform calculation using operands passed as arguments.
     * 
     * @param a Left operand
     * @param b Right operand
     * @return  Calculated value
     */
    public double calculate(double a, double b)
    {
        switch (value)
        {
            case '+': return a+b;
            case '-': return a-b;
            case '*': return a*b;
            case '/': return a/b;
            case '^': return Math.pow(a, b);
        }
        return 0;
    }
}
