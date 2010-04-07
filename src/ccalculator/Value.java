package ccalculator;

public abstract class Value extends Token
{    
    protected String findRegex(String where, String regex)
    {
         int length = where.length()-where.replaceFirst(regex, "").length();
         return where.substring(0, length);
    }

    abstract double value();
}
