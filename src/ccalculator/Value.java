package ccalculator;

/**
 * Represents an abstract token which can return a numeric value.
 */
public abstract class Value extends Token
{
	/**
	 * Get stored or calculated numeric value.
	 */
    abstract double value();
}
