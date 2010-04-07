package ccalculator;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.*;

public class Function extends Value
{
    protected boolean minus = false;
    private ArrayList<Value> args = new ArrayList<Value>();
    private String function_name;
    
    public Function(String str) throws ParseException
    {
    	this(str, true);
    }
    
    public Function(String str, boolean sign) throws ParseException
    {
        Matcher matcher = Pattern.compile("\\s*([A-Za-z]+)\\(").matcher(str);
        if (!matcher.lookingAt())
        {
            throw new ParseException("function", offset);
        }
        function_name = matcher.group(1);
        length = matcher.end();
        args.add(findBlock(str.substring(length)));
        do
        {
            if (findComma(str.substring(length)))
            {
            	args.add(findBlock(str.substring(length)));
            }
            else
            {
            	findClosingBracket(str.substring(length));
            	break;
            }
        }
        while (false);
    }
    
    public double value()
    {
        return function_name.length();
    }
}
