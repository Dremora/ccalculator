package ccalculator;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class for all tokens of the expression.
 *
 * Expression is stored in a tree of Token subclasses.
 */
public abstract class Token
{
	/**
	 * Length of token.
	 */
    protected int length = 0;
    
    /**
     * Current position in string. Is changed after any successful find or parse operation.
     */
    protected static int offset = 0;

    /**
     * Find length in bytes of current token and all its children.
     * 
     * @return Length in bytes of current token and all its children.
     */
    public int length()
    {
        return length;
    }
    
    /**
     * Parse string for the Value token.
     * 
     * @param str             String to parse
     * @param sign            If true, token can begin with a sign
     * @return                Found Value token
     * @throws ParseException Throws if unable to find Value token
     */
    protected Value findValue(String str, boolean sign) throws ParseException
    {
        try
        {
            Number number = new Number(str, sign);
            length += number.length();
            return number;
        }
        catch (ParseException e)
        {
            try
            {
                Brackets brackets = new Brackets(str, sign);
                length += brackets.length();
                return brackets;
            }
            catch (ParseException exc)
            {
            	try
            	{
	                Function function = new Function(str, sign);
	                length += function.length();
	                return function;
            	}
	            catch (ParseException ex)
	            {
	                if (ex.getMessage().equals("number") || ex.getMessage().equals("(") || ex.getMessage().equals("function"))
	                {
	                    throw new ParseException("Number, opening bracket or function expected but not found", ex.getErrorOffset());
	                }
	                else if (ex.getMessage().equals("operator") || ex.getMessage().equals(")"))
	                {
	                    throw new ParseException("Operator or closing bracket expected but not found", ex.getErrorOffset());
	                }
	                else throw ex;
	            }
	        }
        }
    }
    
    /**
     * Parse string for a Block token.
     * 
     * @param str             String to parse
     * @return                Found Block token
     * @throws ParseException Throws if unable to find a Block token
     */
    protected Block findBlock(String str) throws ParseException
    {
        try
        {
            Block block = new Block(str);
            length += block.length();
            return block;
        }
        catch (ParseException e)
        {
            throw new ParseException("Number, opening bracket or function expected but not found", e.getErrorOffset());
        }
    }
    
    /**
     * Parse string for a closing ) bracket.
     * 
     * @param str             String to parse
     * @throws ParseException Throws if unable to find a closing bracket
     */
    protected void findClosingBracket(String str) throws ParseException
    {
        Matcher matcher = Pattern.compile("\\s*\\)").matcher(str);
        if (!matcher.lookingAt())
        {
            throw new ParseException(")", offset);
        }
        offset += matcher.end();
        length += matcher.end();
    }
    
    /**
     * Parse string for a comma character.
     * @param str String to parse
     * @return    True if comma is found, false otherwise
     */
    protected boolean findComma(String str)
    {
        Matcher matcher = Pattern.compile("\\s*,").matcher(str);
        if (matcher.lookingAt())
        {
            offset += matcher.end();
        	length += matcher.end();
        	return true;
        }
        else return false;
    }
    
    /**
     * Find any whitespace.
     * 
     * @param str String to parse.
     */
    protected void findWhitespace(String str)
    {
        Matcher matcher = Pattern.compile("\\s*").matcher(str);
        matcher.lookingAt();
        offset += matcher.end();
        length += matcher.end();
    }
}
