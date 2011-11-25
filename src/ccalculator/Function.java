package ccalculator;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.*;

/**
 * Represents a function token.
 * 
 * Contains function name, argument list and a prefix sign.
 */
public class Function extends Value
{
	/**
	 * True if function is prefixed with minus sign.
	 */
    protected boolean minus = false;
    /**
     * Array of arguments, stored as Blocks.
     */
    private ArrayList<Block> args = new ArrayList<Block>();
    /**
     * The actual Java method which represents the function.
     */
    private java.lang.reflect.Method method;
    
    /**
     * Constructs a function class by trying to parse a function from the beginning of a string.
     * 
     * @param str             String to parse
     * @throws ParseException Throws if unable to parse a function
     */
    public Function(String str) throws ParseException
    {
    	this(str, true);
    }
    
    /**
     * Constructs a function class by trying to parse a function from the beginning of a string.
     * 
     * @param str             String to parse
     * @param sign            True if number is allowed to be signed
     * @throws ParseException Throws if unable to parse a function or find a function with specified name and arguments
     */
    public Function(String str, boolean sign) throws ParseException
    {
        Matcher matcher = Pattern.compile("\\s*([A-Za-z]+)\\(").matcher(str);
        if (!matcher.lookingAt())
        {
            throw new ParseException("function", offset);
        }
        Class<?> functionClass;
        String functionName = matcher.group(1).toLowerCase();
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
        
    	ArrayList<Class<?>> argsTypes = new ArrayList<Class<?>>();
    	for (int i = 0; i < args.size(); i++)
    	{
    		argsTypes.add(double.class);
    	}
    	Class<?>[] argsTypesArray = argsTypes.toArray(new Class<?>[0]);
    	try
        {
            method = functionClass.getMethod(functionName, argsTypesArray);
        }
    	catch (NoSuchMethodException e)
    	{
    		throw new ParseException("Wrong number of arguments for function " + functionName, offset);
    	}
    }
    
    /**
     * Calculates and returns the function value.
     */
    public double value()
    {
    	ArrayList<Object> argsValues = new ArrayList<Object>();
    	for (Block arg: args)
    	{
    		argsValues.add(arg.value());
    	}
    	try
        {
            return (Double) method.invoke(null, argsValues.toArray());
        } catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (java.lang.reflect.InvocationTargetException e) {
		}
        return 0;
    }
}
