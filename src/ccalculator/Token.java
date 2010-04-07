package ccalculator;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Token
{
    protected int length = 0;
    protected int offset = 0;

    public int length()
    {
        return length;
    }
    
    public int offset()
    {
        return offset;
    }
    
    protected int position()
    {
        return offset + length;
    }
    
    protected Value findValue(String str, boolean sign) throws ParseException
    {
        try
        {
            Number number = new Number(str, sign, position());
            length += number.length();
            return number;
        }
        catch (ParseException e)
        {
            try
            {
                Brackets brackets = new Brackets(str, position(), sign);
                length += brackets.length();
                return brackets;
            }
            catch (ParseException exc)
            {
            	try
            	{
	                Function function = new Function(str, position(), sign);
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
    
    protected Value findBlock(String str) throws ParseException
    {
        try
        {
            Brackets brackets = new Brackets(str, position(), true, false);
            length += brackets.length();
            return brackets;
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
            throw new ParseException(")", position());
        }
        length += matcher.end();
    }
    
    protected boolean findComma(String str) throws ParseException
    {
        Matcher matcher = Pattern.compile("\\s*,").matcher(str);
        if (matcher.lookingAt())
        {
        	length += matcher.end();
        	return true;
        }
        else return false;
    }
}
