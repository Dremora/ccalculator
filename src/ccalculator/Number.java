package ccalculator;
import java.text.ParseException;
import java.util.regex.*;

/**
 * Represents a decimal number token.
 * 
 * Number can be integer or fraction, signed or non-signed.
 * Number value is stored as double.
 */
public class Number extends Value 
{
	/**
	 * Stores the number value.
	 */
    protected double value;
 
    /**
     * Constructs a number class by trying to parse a number from the beginning of a string.
     * 
     * @param str             String to parse
     * @param sign            True if number is allowed to be signed
     * @throws ParseException Throws if unable to parse a number
     */
    public Number(String str, boolean sign) throws ParseException
    {
        Matcher matcher = Pattern.compile(sign ? "\\s*([\\+\\-]?[0-9]+(\\.[0-9]+)?)" : "\\s*([0-9]+(\\.[0-9]+)?)").matcher(str);
        if (!matcher.lookingAt())
        {
            throw new ParseException("number", offset);
        }
        value = Double.parseDouble(matcher.group(1));
        length = matcher.end();
        offset += length;
    }
    
    /**
     * Returns the value of a number.
     */
    public double value()
    {
        return value;
    }
}
