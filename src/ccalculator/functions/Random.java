package ccalculator.functions;

/**
 * Function random.
 * 
 * Returns random value in the interval from min to max.
 */
public class Random {
	public static double random(double min, double max)
	{
		return min + Math.random() * (max-min);
	}
}
