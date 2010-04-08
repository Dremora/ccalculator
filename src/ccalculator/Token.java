package ccalculator;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Token
{
    protected int length = 0;
    protected static int offset = 0;

    public int length()
    {
        return length;
    }
    
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
}
