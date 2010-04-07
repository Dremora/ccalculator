package ccalculator;
import java.text.ParseException;
import java.util.*;
import java.util.regex.*;

public class Block extends Value 
{
    protected ArrayList<Value> values = new ArrayList<Value>();
    protected ArrayList<Operator> operators = new ArrayList<Operator>();

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
    
    private void findOperator(String str) throws ParseException
    {
        Operator operator = new Operator(str);
        operators.add(operator);
        length += operator.length();
    }
    
    private void findWhitespace(String str) throws ParseException
    {
        Matcher matcher = Pattern.compile("\\s*").matcher(str);
        matcher.lookingAt();
        offset += matcher.end();
        length += matcher.end();
    }

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
