package ccalculator;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.*;

public class Function extends Value
{
    protected boolean minus = false;
    private ArrayList<Block> args = new ArrayList<Block>();
    private Class<?> functionClass;
    private String functionName;
    
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
        functionName = matcher.group(1).toLowerCase();
        try
        {
        	String functionNameCapitalized = functionName.substring(0, 1).toUpperCase() + functionName.substring(1);
        	functionClass = Class.forName("ccalculator.functions." + functionNameCapitalized);
        }
        catch (ClassNotFoundException e)
        {
        	throw new ParseException("Unknown function: " + functionName, offset);
        }
        length = matcher.end();
        offset += matcher.end();
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
        while (true);
    }
    
    public double value()
    {
        try
        {
            java.lang.reflect.Method method = functionClass.getMethod(functionName, double.class);
            return (Double) method.invoke(null, args.get(0).value());
    	}
    	catch (Exception e)
    	{
    	}
        return 0;
    }
}
