package ccalculator;
import java.text.ParseException;
import java.util.regex.*;

public class Brackets extends Value 
{
    protected boolean minus = false;
    protected Block block;

    public Brackets(String str) throws ParseException
    {
        this(str, false);
    }
    
    public Brackets(String str, boolean sign) throws ParseException
    {
        findOpeningBracket(str, sign);
        block = findBlock(str.substring(length));
        findClosingBracket(str.substring(length));
    }
    
    private void findOpeningBracket(String str, boolean sign) throws ParseException
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
        offset += matcher.end();
        length += matcher.end();
    }

    public double value()
    {
        return minus ? -block.value() : block.value();
    }
}
