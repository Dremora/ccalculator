package ccalculator;
import java.text.ParseException;
import java.util.regex.*;

public class Number extends Value 
{
    protected double value;
 
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

    public double value()
    {
        return value;
    }
}
