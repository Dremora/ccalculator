package ccalculator;
import java.text.ParseException;
import java.util.regex.*;

public class Operator extends Token
{
    private char value;
    private static char[][] priorities = {
        {'^'},
        {'*', '/'},
        {'+', '-'}
    };
    
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
    
    public static int total_priorities()
    {
        return priorities.length;
    }
    
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
