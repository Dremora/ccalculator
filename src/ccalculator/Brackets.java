package ccalculator;
import java.text.ParseException;
import java.util.regex.*;

/**
 * Encapsulates Block token in brackets.
 */
public class Brackets extends Value 
{
	/**
	 * True if brackets are preceded by minus sign.
	 */
    protected boolean minus = false;
    
    /**
     * Stores Block which is encapsulated by Brackets class.
     */
    protected Block block;

    /**
     * Creates Bracket object by parsing the string.
     * 
     * @param str             String to search in.
     * @throws ParseException Throws on parse error
     */
    public Brackets(String str) throws ParseException
    {
        this(str, false);
    }
    
    /**
     * Creates Brackets object by parsing the string.
     * 
     * @param str             String to search in.
     * @param sign            If true, brackets can be preceded by a sign.
     * @throws ParseException Throws on parse error
     */
    public Brackets(String str, boolean sign) throws ParseException
    {
        findOpeningBracket(str, sign);
        block = findBlock(str.substring(length));
        findClosingBracket(str.substring(length));
    }
    
    /**
     * Try finding the opening bracket ( sign.
     * 
     * @param str             String to search in.
     * @param sign            If true, brackets can be preceded by a sign.
     * @throws ParseException Throws if bracket can't be found
     */
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

    /**
     * Returns calculated value of brackets contents.
     */
    public double value()
    {
        return minus ? -block.value() : block.value();
    }
}
