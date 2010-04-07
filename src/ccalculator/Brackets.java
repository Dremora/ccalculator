package ccalculator;
import java.text.ParseException;
import java.util.*;
import java.util.regex.*;

public class Brackets extends Value 
{
    protected boolean minus = false;
    protected ArrayList<Value> values = new ArrayList<Value>();
    protected ArrayList<Operator> operators = new ArrayList<Operator>();

    public Brackets(String str) throws ParseException
    {
        this(str, 0, false, true);
    }

    public Brackets(String str, int offset) throws ParseException
    {
        this(str, offset, false, true);
    }

    public Brackets(String str, int offset, boolean sign) throws ParseException
    {
        this(str, offset, sign, true);
    }
    
    public Brackets(String str, int offset, boolean sign, boolean brackets) throws ParseException
    {
        this.offset = offset;
        if (brackets)
        {
            length += findOpeningBracket(str, sign);
        }
        values.add(findValue(str.substring(length), true));
        if (brackets) try
        {
            findClosingBracket(str.substring(length));
            return;
        }
        catch (ParseException e)
        {
        }
        while (length < str.length())
        {
            findOperator(str.substring(length));
            values.add(findValue(str.substring(length), false));
            
            if (brackets)
            {
                try
                {
                    findClosingBracket(str.substring(length));
                    return;
                }
                catch (ParseException e)
                {
                }
            }
            else
            {
                findWhitespace(str.substring(length));
            }
        }
        if (brackets)
        {
            throw new ParseException("Closing bracket expected but not found", position());
        }
        else
        {
            length = str.length();
        }
    }
    
    private void findOperator(String str) throws ParseException
    {
        Operator operator = new Operator(str, position());
        operators.add(operator);
        length += operator.length();
    }
    
    private void findWhitespace(String str) throws ParseException
    {
        Matcher matcher = Pattern.compile("\\s*").matcher(str);
        matcher.lookingAt();
        length += matcher.end();
    }
    
    private int findOpeningBracket(String str, boolean sign) throws ParseException
    {
        Matcher matcher = Pattern.compile(sign ? "\\s*([+-]?)\\(" : "\\s*\\(").matcher(str);
        if (!matcher.lookingAt())
        {
            throw new ParseException("(", offset);
        }
        if (sign)
        {
            minus = (matcher.group(1).equals("-"));
        }
        return matcher.end();
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
        return minus ? -numbers.get(0) : numbers.get(0);
    }
}
