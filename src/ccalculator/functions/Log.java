package ccalculator.functions;

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
