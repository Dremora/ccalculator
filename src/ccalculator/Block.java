package ccalculator;
import java.text.ParseException;
import java.util.*;

/**
 * Represents a token consisting of values separated by operators.
 */
public class Block extends Value 
{
	/**
	 * Array of Value tokens. Stored in the order of appearance in the expression.
	 */
    protected ArrayList<Value> values = new ArrayList<Value>();
    
    /**
     * Array of Operator tokens. Stored in the order of appearance in the expression.
     */
    protected ArrayList<Operator> operators = new ArrayList<Operator>();

    /**
     * Creates Block from the string.
     * 
     * @param str             String to search Block in.
     * @throws ParseException Throws on parse error.
     */
    public Block(String str) throws ParseException
    {
        values.add(findValue(str.substring(length), true));
        while (length < str.length())
        {
        	try
        	{
        		findOperator(str.substring(length));
        	}
        	catch (ParseException e)
        	{
        		break;
        	}
            values.add(findValue(str.substring(length), false));
            findWhitespace(str.substring(length));
        }
    }
    
    /**
     * Tries to parse operator from the string.
     * 
     * @param str             String to parse
     * @throws ParseException Throws if unable to parse operator
     */
    private void findOperator(String str) throws ParseException
    {
        Operator operator = new Operator(str);
        operators.add(operator);
        length += operator.length();
    }

    /**
     * Returns calculated value of the block.
     */
    public double value()
    {
        ArrayList<Double> numbers = new ArrayList<Double>(values.size());
        for (int i = 0; i < values.size(); i++)
        {
            numbers.add(values.get(i).value());
        }
        for (int i = Operator.total_priorities(); i > 0; i--)
        {
            int skipped = 0;
            while (operators.size() > skipped)
            {
                if (operators.get(skipped).priority() == i)
                {
                    numbers.set(skipped, operators.get(skipped).calculate(numbers.get(skipped), numbers.get(skipped+1)));
                    numbers.remove(skipped+1);
                    operators.remove(skipped);
                }
                else
                {
                    skipped++;
                }
            }
        }
        return numbers.get(0);
    }
}
