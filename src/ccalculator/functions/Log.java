package ccalculator.functions;

/**
 * Function log.
 * 
 * Returns logarithm for a value.
 * 
 * By default calculates natural logarithm, optionally second argument can be passed with base value.
 */
public class Log {
	public static double log(double value)
	{
		return Math.log(value);
	}
	
	public static double log(double value, double base)
	{
		return Math.log(value) / Math.log(base);
	}
}
